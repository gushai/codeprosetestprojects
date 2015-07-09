package org.codeprose.rational

/*
 * Rational
 * --------
 *
 * A sample application highlighting some features of <strong>org.codeprose.rational.Rational</strong>.
 */  
object Example {
  
  def main(args: Array[String]): Unit = {
  
    println("[org.codeprose.rational.Example]: Sample Application.")
    val A = new Rational(1,7) 
    val B = new Rational(1)
    val C = A + B
    val D = A.add(B)
   
    // Implicit conversion
    val E = 5 + D  
    
    println(A + " + " + B + " = " + C)
    println(C + " + " + D + " = " + (C + D))
    println(5 + " + " + D + " = " + E)

  }
}
