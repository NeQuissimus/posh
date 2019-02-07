import nequi.posh._
import nequi.posh.combinators._
import nequi.posh.iterable._
import nequi.posh.implicits._

object Main extends App {
  val a: Either[String, String Refined Empty]                                      = ""
  val b: Either[String, String Refined MinSize[4]]                                 = "abc"
  val c: Either[String, String Refined (Empty Or MinSize[3])]                      = "abc"
  val d: Either[String, String Refined (MinSize[2] Or Empty)]                      = "a"
  val e: Either[String, String Refined (Empty And MinSize[0])]                     = ""
  val f: String Refined Empty                                                      = Refined.unsafeCoerce("")
  val g: Either[String, String Refined (MinSize[3] And MaxSize[3])]                = "abc"
  val h: Either[String, String Refined Not[MinSize[2]]]                            = "abc"
  val i: Either[String, String Refined (MinSize[6] Xor MaxSize[6])]                = "abcdef"
  val j: Either[String, String Refined OneOf4[Size[4], Size[5], Size[6], Size[7]]] = "abcd"

  println(a)
  println(b)
  println(c)
  println(d)
  println(e)
  println(f)
  println(g)
  println(h)
  println(i)
  println(j)
}
