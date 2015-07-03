/*
 * Rational 
 * ========
 * 
 * **Rational** implements **rational numbers** in Scala to introduce some of the 
 * basic Scala concepts. More information on codeprose can be found on [github](http://example.com/ "codeprose on github").
 *
 *It offers:
 *
 * +	Addition (<code>add()</code> and <code>+</code>)
 * +	Substraction (<code>substract()</code> and <code>-</code>)
 * +	Multiplication (<code>multiply()</code> and <code>*</code>)
 * +	Implicit conversion of <code>Long</code> and <code>Int</code>
 * +	Automatic reduction
 * +	And more ...
 */

package org.codeprose.rational

/*
 * Rational
 * --------
 *
 * A sample application highlighting some features of <strong>org.codeprose.rational.Rational</strong>.
 */
object Rational {
  
  def main(args: Array[String]): Unit = {
	
    println("[org.codeprose.rational.Rational]: Sample Application.")
    val A = new Rational(1,7) 
    val B = new Rational(1)
    val C = A + B
    val D = A.add(B)
   
    println(A + " + " + B + " = " + C)
    println(A + " + " + B + " = " + D)

  }
}

/*
 * Rational
 * --------
 *
 * Rational repesents a rational number in Scala.
 * 
 */
class Rational (n: Long, d: Long){

  require(d!=0)
  private val g = gcd(n,d)
  val numerator   = n/g
  val denominator = d/g
  
  /*
   * Overloaded constructor to allow initialization via a single Long.
   */
  def this(n: Long) = this(n,1)
  
  /*
   * ### Addition 
   * 
   * Addition of 2 Rational numbers.
   */
  def add (that: Rational) : Rational = { 
    new Rational(
        this.numerator*that.denominator + that.numerator*this.denominator,
        this.denominator*that.denominator
        )
  }
  
  /*
   * Overloaded Addition
   * 
   * Allows the addition of a Long to a Rational number.
   */
  def add (that: Long) : Rational = {
    this.add(new Rational(that))
  }
    
  /*
   * ### Multiplication
   */
  def multiply (that: Rational) : Rational = {
      new Rational(
          this.numerator * that.numerator,
          this.denominator * that.denominator
          )
  }

  /*
   * Overloaded Multiplication
   * 
   * Allows the multiplication of a Rational number with a Long.
   */
  def multiply(that: Long) : Rational = 
     this.multiply(new Rational(that))
  
  /*
   * ### Substraction
   */
  def substract (that: Rational) : Rational = {
    new Rational(
        this.numerator*that.denominator - this.denominator*that.numerator,
        this.denominator*that.numerator
        )
  }
  /*
   * Overloaded Substraction
   * 
   * Allows the substraction of a Long from a Rational number.
   */
  def substract (that: Long) : Rational = this.substract(new Rational(that))

  /*
   * ### Operator Overloading
   *
   * For:
   *
   * +	Addition
   * +	Substraction
   * +	Multiplication
   */
  def + (that: Rational) : Rational = add(that)
  def + (that: Long) : Rational = add(that)

  def * (that: Rational) : Rational = multiply(that)  
  def * (that: Long) : Rational = multiply(that)

  def - (that: Rational) : Rational = substract(that)
  def - (that: Long) : Rational = substract(that)

  /*
   * Visiable appealing toString() output.
   */
  override def toString = numerator + "/" + denominator
  
  /*
   * ### GCD: Greatest Common Divisor
   *
   * Calcuates the gcd. Used in the constructor to automatically reduce the represented fration.
   *
   */
  /**
   * Calculates the greatest common divisor.
   * @param a A whole number
   * @param b A whole number
   * @return The largest positive integer that divides a and b without a remainder.
   */	
  private def gcd(a: Long, b: Long) : Long = {
    if(b==0) a else gcd(b, a % b)
  }
    
  /*
   * ### Implicit conversions
   *
   * The implicit conversions with allowing a simple addtion of a Long with a Rational number.
   */
  // Implicit conversions 
  // implicit def longToRational(l: Long) = new Rational(l)
  
}

