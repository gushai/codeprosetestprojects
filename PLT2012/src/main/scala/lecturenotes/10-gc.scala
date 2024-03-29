package org.codeprose.plt2012.lecturenotes

object lec10_gc {
/*
These are lecture notes for the "Programming Languages and Types"
course by Klaus Ostermann at the University of Marburg

loosely based on Sec. 13 of "Programming Languages: Application and
Interpretation" by Shriram Krishnamurthi

Please comment/correct/improve these notes via github. Proposals or
questions can be submitted as an "issue"; proposals for
corrections/extensions/improvements can be submitted as a "pull
request". You can of course also send an email to Klaus Ostermann */

/* Let us now consider a more accurate modeling of garbage collection
 * (gc). This time, we will use a mutable store instead of a
 * functional store, because our purpose is not to explain mutation
 * but to explain gc.
 */

import scala.collection.mutable.ArraySeq

/* This is the well-known syntax of our language: FAE with boxes. */

sealed abstract class Exp
case class Num(n: Int) extends Exp
case class Id(name: Symbol) extends Exp
case class Add(lhs: Exp, rhs: Exp) extends Exp
case class Mul(lhs: Exp, rhs: Exp) extends Exp
case class If0(cond: Exp, thenExp: Exp, elseExp: Exp) extends Exp
implicit def num2exp(n: Int) = Num(n)
implicit def id2exp(s: Symbol) = Id(s)
case class Fun(param: Symbol, body: Exp) extends Exp
case class App (funExpr: Exp, argExpr: Exp) extends Exp
def wth(x: Symbol, xdef: Exp, body: Exp) : Exp = App(Fun(x,body),xdef)

case class NewBox(e: Exp) extends Exp
case class SetBox(b: Exp, e: Exp) extends Exp
case class OpenBox(b: Exp) extends Exp
case class Seq(e1: Exp, e2: Exp) extends Exp

/* We will equip our values with a mutable flag that is useful for
 * mark-and-sweep garbage collection. In real systems it is
 * implemented as a bit flag, or, if the so-called "tri-color
 * algorithm" is used, with two bit flags.
 */

abstract class Value { 
  var marked : Boolean = false
}

/* We will also use a mutable map instead of a map for environments.
 * This is not needed for mark-and-sweep, but for copying garbage
 * collectors such as Cheney's semi-space garbage collection
 * algorithm.
 */

type Env = scala.collection.mutable.Map[Symbol, Value]
case class NumV(n: Int) extends Value
case class ClosureV(f: Fun, env: Env) extends Value
case class AddressV(a: Int) extends Value

/* To be able to experiment with different store and gc designs, we
 * create an interface for stores. The stack parameter in malloc is
 * needed during gc to determine the root nodes from which the
 * algorithms can start.
 */

trait Store {
  def malloc(stack: List[Env], v: Value) : Int
  def update(index: Int, v: Value) : Unit
  def apply(index: Int) : Value
}

/* In our interpreter, the stack of environments is only implicitly
 * available on the stack of the meta-language. To reify the call-
 * stack we need to make it explicit. We do so by constructing the
 * stack explicitly and passing it as parameter. The first element
 * of the stack is the current environment; the rest is only needed
 * for gc.
 */

def eval(e: Exp, stack: List[Env], store: Store) : Value = e match {

  case Num(n) => NumV(n)

  case Id(x) => stack.head(x)

  case f@Fun(_, _) => ClosureV(f, stack.head)

  /* With a mutable store, we do not have to thread it according to
   * the order of evaluation any more.
   */

  case If0(cond, thenExp, elseExp)
    => eval(cond, stack, store) match {
         case NumV(0) => eval(thenExp, stack, store)
         case _       => eval(elseExp, stack, store)
       }

  /* The mutable store allows us to take advantage of Scala's
   * evaluation order and perform two pattern matchings
   * simultaneously.
   */

  case Add(l, r)
    => (eval(l, stack, store), eval(r, stack, store)) match {
         case (NumV(v1), NumV(v2)) => NumV(v1 + v2)
         case _ => sys.error("can only add numbers")
       }

  case Mul(l, r)
    => (eval(l, stack, store), eval(r, stack, store)) match {
         case (NumV(v1), NumV(v2)) => NumV(v1 * v2)
         case _ => sys.error("can only multiply numbers")
       }

  /* A new environment should be pushed onto the stack only when
   * binding occurs. Where exactly in BCFAE do bindings happen?
   */

  case App(f, a)
    => eval(f, stack, store) match {
         case ClosureV(f, cEnv)
           => eval(
                f.body,
                (cEnv + (f.param -> eval(a, stack, store))) :: stack,
                store
              )
         case _ => sys.error("can only apply functions")
       }

  /* The mutable store allows us to implement Seq-expression
   * in terms of sequencing in Scala itself.
   */

  case Seq(e1, e2)
    => eval(e1, stack, store); eval(e2, stack, store)

  case NewBox(e: Exp)
    => {
         val a = store.malloc(stack, eval(e, stack, store))
         AddressV(a)
       }

  case SetBox(b: Exp, e: Exp)
    => eval(b, stack, store) match {
         case AddressV(a)
           => {
                val ev = eval(e, stack, store)
                store.update(a, ev)
                ev
              }
         case _ => sys.error("can only set boxes")
       }

  case OpenBox(b: Exp)
    => eval(b, stack, store) match {
         case AddressV(a) => store(a)
         case _ => sys.error("can only open boxes")
       }
}

/* Here is one implementation of the Store interface that does not
 * perform gc. It just runs out of memory once the store is full.
 */

class NoGCStore(size: Int) extends Store {

  val memory = new ArraySeq[Value](size)

  var nextFreeAddr : Int = 0

  def malloc(stack: List[Env], v: Value) : Int = {
    val x = nextFreeAddr
    if (x >= size) sys.error("out of memory")
    nextFreeAddr += 1
    update(x, v)
    x
  }

  def update(index: Int, v: Value) : Unit = memory.update(index, v)

  def apply(index: Int) = memory(index)
}

/* Here is a mark-and-sweep garbage collector.
 */

class MarkAndSweepStore(size: Int) extends Store {

  val memory = new ArraySeq[Value](size)

  var free : Int = size

  var nextFreeAddr : Int = 0

  def malloc(stack: List[Env], v: Value) : Int = {
    if (free <= 0) gc(stack)
    if (free <= 0) sys.error("out of memory")

    /* Here we find the next available location in memory via a while-
     * loop. In order to avoid maintaining a list of available spaces
     * (because we are lazy), let us assume that no box created in
     * BCFAE can has an address pointing to a null memory cell (which
     * also is the case).
     *
     * If we ensure the invariant that the variable `free` has always
     * the number of free memory space, then the following loop will
     * always halt. The nontermination situation will generate an out-
     * of-memory error and the program will abort.
     */

    while (memory(nextFreeAddr) != null) {
      nextFreeAddr += 1
      if (nextFreeAddr == size) nextFreeAddr = 0
    }

    free -= 1
    update(nextFreeAddr, v)
    nextFreeAddr
  }

  def update(index: Int, v: Value) : Unit = memory.update(index, v)

  def apply(index: Int) = memory(index)

  def allAddrInVal(v: Value) : Set[Int] = v match {
    case AddressV(a)      => Set(a)
    case NumV(_)          => Set.empty
    case ClosureV(f, env) => allAddrInEnv(env)
  }

  def allAddrInEnv(env: Env) : Set[Int] =
    env.values.map(allAddrInVal _).fold(Set.empty)(_ union _)

  def mark(seed: Set[Int]) : Unit = {
    seed.foreach(memory(_).marked = true)
    val newAddresses = seed.flatMap(
                         ad => allAddrInVal(memory(ad))
                       ).filter(!memory(_).marked)
    if(newAddresses != Set.empty) {
      mark(newAddresses)
    }
  }

  /* What graph algorithm underlies the mark step as implemented here?
   * What potential problem it could cause in a "real" interpreter?
   */

  def sweep() : Unit = {
    memory.indices.foreach(
      index => if (!memory(index).marked) {
                 free += 1
                 memory(index) = null
               }
               else {
                 /* Reset `marked` flag for the next gc */
                 memory(index).marked = false
               }
    )
  }

  def gc(stack: List[Env]) : Unit = {
    println("\nSTARTING GC\nSTACK = " + stack + "\nSTORE = " + memory)
    mark(stack.map(allAddrInEnv _).fold(Set.empty)(_ union _))
    sweep()
    println("GC COMPLETE\nSTORE = " + memory +
            "\nNUMBER OF FREE SLOTS = " + free)
  }
}

val test4 = wth('makedata, Fun('x, NewBox(NewBox(NewBox('x)))),
                Seq(App('makedata, 1),
                Seq(App('makedata, 2),
                Seq(wth('s, App('makedata, 3),
                            App('makedata, 's)),
                    App('makedata, 4)))))

def runTest4 = eval(
                 test4,
                 List(scala.collection.mutable.Map.empty),
                 new MarkAndSweepStore(5)
               )

/* This model of garbage collection does not illustrate the difficulty
 * of memory management. In most languages, the size of the allocated
 * memory regions on the heap vary, and hence one needs an algorithm
 * to find a free and large-enough spot on the heap. There are various
 * algorithms and heuristics (best-fit, worst-fit, first-fit, ...) for
 * that purpose.
 *
 * There are also various alternative gc designs. Mark-and-sweep is a
 * non-moving algorithm, where reachable heap objects are never moved.
 * In contrast to that, copying gc algorithms move the reachable
 * objects to a different portion of the heap. One of the oldest
 * algorithms is the semi-space garbage collector, in particular with
 * the implementation purpose.

     http://www.cs.umd.edu/class/fall2002/cmsc631/cheney/cheney.html

 * Topic for class discussion: What are the pros and cons of moving
 * vs. non-moving gc?
 *
 * It can be shown empirically that most unreachable objects become
 * unreachable while they are still young. Generational gc algorithms
 * take this empirical fact into account and divide the objects into
 * generations, whereby the (small) youngest generation of objects is
 * garbage-collected more frequently.
 *
 * A typical problem of the simple gc algorithms we discussed is the
 * stop-the-world phenomenon: All execution has to be stopped during a
 * gc cycle. This issue is addressed by incremental or concurrent
 * garbage collectors. Incremental garbage collectors typically reduce
 * the total throughput but increase responsiveness and real-time
 * behavior.
 *
 * A completely different approach to memory management is _reference
 * counting_. In reference counting, each object on the heap (in our
 * case, each box) maintains a counter which says how many pointers
 * currently point to that object. The counter is adjusted whenever a
 * pointer variable is assigned to this object (incremented), or from
 * this object to another object (decremented). When the counter is 0,
 * the object can be reclaimed.
 *
 * The obvious disadvantage of reference counting is that it cannot
 * detect cycles on the heap. Hence reference counting algorithm must
 * be augmented with some means to detect cycles.
 *
 * Topic for class discussion: What are the pros and cons of reference
 * counting vs. tracing garbage collectors such as mark-and-sweep or
 * semi-space?
 */

}