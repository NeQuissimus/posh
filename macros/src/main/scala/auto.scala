package nequi.posh

package object auto {
  implicit def autoRefine[A, B <: Refinement](a: A)(
      implicit f: A => A Refined B): A Refined B = macro AutoRefine.impl[A, B]
}
