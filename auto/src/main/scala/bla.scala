package foo

import nequi.posh._
import nequi.posh.auto._
import nequi.posh.implicits._
import nequi.posh.iterable.Empty

object Foo {
  type NonEmptyString = String Refined Empty

  def main(args: Array[String]) = {
    val a: NonEmptyString = ""
    println(a)
  }
}
