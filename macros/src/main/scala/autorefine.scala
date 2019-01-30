package nequi.posh

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

final class AutoRefinementError(msg: String) extends RuntimeException {
  override def toString(): String = s"AutoRefinementError: ${msg}"
}

object Auto {
  def autoRefineImpl[A, B <: Refinement](a: A, f: A => Either[String, A Refined B]): A Refined B = macro AutoRefine.impl[A, B]
}

final class AutoRefine(val c: blackbox.Context) {
  import c.universe._

  def impl[A: c.WeakTypeTag, B <: Refinement](a: c.Expr[A], f: c.Expr[A => Either[String, A Refined B]]): c.Expr[A Refined B] = {
      c.Expr[A Refined B](q"""f(a).fold(err => { throw new AutoRefinementError(err); null.asInstanceOf[A Refined B] }, identity)""")
  }
}
