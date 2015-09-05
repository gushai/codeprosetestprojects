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
  
 /* Can not return list as this kills the ensime client! spray.json can not parse the return object
  *  
  */
  /* def packageTogether(a:Int, b: Int) : List[Int] = {
    
    // List(c.a,a,b)
 
  } */
  
  
  
  def doesNothing(a: Int) : Unit = {
    
  }
  
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
    
    
    // Removed see above.
    //val listOfInts = foo.packageTogether(2,3)
    
    
    foo.doesNothing(0)
    
    
    // Implicit conversion
    val A = Rational(5)
    val B = 314 + A



  }
}

