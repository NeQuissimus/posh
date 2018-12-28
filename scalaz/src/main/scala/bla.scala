import nequi.posh._
import nequi.posh.implicits._
import nequi.posh.iterable._
import nequi.posh.sz.show._

import scalaz.std.string._
import scalaz.syntax.show._

object Main extends App {
  val a: Either[String, String Refined MinSize[3]] = "abc"

  println(a.map(_.show))
  println(a.toOption.get.getClass)
}
