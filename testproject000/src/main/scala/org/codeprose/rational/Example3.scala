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

    // Implicit conversion
    val A = new Rational(5)
    val B = 5 + A



  }
}

