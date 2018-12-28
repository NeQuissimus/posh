import nequi.posh._

object Main extends App {
  import Combinators._
  import Iterable._

  val a: Either[String, String Refined Iterable.Empty] = ""
  val b: Either[String, String Refined Iterable.MinSize[4]] = "abc"
  val c: Either[String, String Refined (Empty Or MinSize[3])] = "abc"
  val d: Either[String, String Refined (MinSize[2] Or Empty)] = "a"
  val e: Either[String, String Refined (Empty And MinSize[0])] = ""
  val f: String Refined Empty = Refined.unsafeCoerce("")
  val g: Either[String, String Refined (MinSize[3] And MaxSize[3])] = "abc"
  val h: Either[String, String Refined Not[MinSize[2]]] = "abc"

  println(a)
  println(b)
  println(c)
  println(d)
  println(e)
  println(f)
  println(g)
  println(h)
}
