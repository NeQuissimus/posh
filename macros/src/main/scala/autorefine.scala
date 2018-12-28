package nequi.posh

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

class AutoRefine(val c: blackbox.Context) {
  import c.universe._

  def impl[A, B <: Refinement](expr: c.Expr[A]): c.Expr[Refined[A, B]] = {
    import implicits._

    ???
  }
}
