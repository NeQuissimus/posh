import nequi.posh._

import scalaz.std.string._
import scalaz.syntax.show._

object Main extends App {
  import Iterable._
  import implicits._

  val a: Either[String, String Refined MinSize[3]] = "abc"

  println(a.map(_.show))
  println(a.toOption.get.getClass)
}
