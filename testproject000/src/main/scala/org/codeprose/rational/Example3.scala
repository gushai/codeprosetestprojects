package org.codeprose.rational

/*
*Implicit Conversion and Parameter
*=================================
*
*/


class Base(){
	val a = 1
}


class SomeClass()(implicit c: Base){
	
  def bar(b: Int) : Int = {
		b+c.a
	}
  
 
  def add(a: Int, b: Int)(implicit shift: Int) : Int = {
    a+b+c.a+shift
  }
  
 /* Can not return list as this kills the ensime client! spray.json can not parse the return object.
  *  
  */
  /* 
   * def packageTogether(a:Int, b: Int) : List[Int] = {
   *  List(c.a,a,b)
   * }
   * 
   */
  
  
  
  def doesNothing(a: Int) : Unit = {
    
  }
  
  
  def fooWithFunctionArugment(a: Int,shifter: (Int) => Int) : Int = {
    shifter(a)
  }
  
  def currying(a: Int)(b: Int)(c: Int) : Int = {
    a+b+c
  }
  
  def curryingWithFunctionArg(shifter: Int => Int)(a: Int)(b: Int) : Int = {
    shifter(b) + shifter(a)
  }
  
  def curryingWithFuncArgImpltArg(
      shifter: Int => Int)(a: Int)(b: Int)(implicit base: Int) : Int = {
    base + shifter(b) + shifter(a)
  }
  
  def notImplemented() = ???
  
}



/*
*Illustration
*------------
*
*/

object Example3 {
  
  def main(args: Array[String]): Unit = {

    // Implicit parameter
    implicit val implicitBase = new Base()

    val foo = new SomeClass()

    foo.bar(1)  

    implicit val shift = 10
    
    val somenumber = foo.add(1,2)
    
    foo.doesNothing(0)
    
    
    val curryPartial = foo.currying(5)_
    val curryFull = curryPartial(5)(5)
    
    val shiftByOne = (x: Int) => x+1
    
    val curryWithFuncPartial = foo.curryingWithFunctionArg(shiftByOne)_
    val curryWithFunc = curryWithFuncPartial(5)(5)
    
 
    val curryWithFuncAndImplicitPartial = foo.curryingWithFuncArgImpltArg(shiftByOne)_
    val curryWithFuncAndImplicit = curryWithFuncAndImplicitPartial(10)(10)
    
    
    val curryWithAnonFuncAndImplPart = foo.curryingWithFuncArgImpltArg((x: Int) => x + 314)_
    val curryWithAnonFuncAndImpl = curryWithAnonFuncAndImplPart(11)(11)
    
    
    
    // Implicit conversion
    val A = Rational(5)
    val B = 314 + A



  }
}

