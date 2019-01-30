package nequi.posh

package object auto {
    implicit def autoRefine[A, B <: Refinement](a: A)(
      implicit f: A => Either[String, A Refined B]): A Refined B = Auto.autoRefineImpl(a, f)
}
