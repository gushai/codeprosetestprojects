package org.codeprose.mickeymouse

trait TA { val value = 5}

class A extends TA {}
class B {} 
class C {}

class ImplicitTest { val secret = ";" }

object Example {
  
  def main(args: Array[String]): Unit = {
	val a = 5
	val b = 10
	val c = a+b

	val d = new A()
	val e = new B()
	val f = new C()
  }

  def foo(start: Int)(implicit impArg: ImplicitTest) : Unit = {
	  val a = start + impArg.secret
  }

}
