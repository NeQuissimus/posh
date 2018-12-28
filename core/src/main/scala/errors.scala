package nequi.posh

trait ErrorMessage[A, B <: Refinement] {
  def message(a: A): String
}

object ErrorMessage {
  def apply[A, B <: Refinement](f: A => String) = new ErrorMessage[A, B] {
    def message(a: A) = f(a)
  }
}

trait ErrorMessages {
  implicit def combinatorsAndError[A, B <: Refinement, C <: Refinement](implicit B: ErrorMessage[A, B], C: ErrorMessage[A, C]) = ErrorMessage[A, Combinators.And[B, C]](a => s"!(${B.message(a)} && ${C.message(a)})")
  implicit def combinatorsNotError[A, B <: Refinement](implicit B: ErrorMessage[A, B]) = ErrorMessage[A, Combinators.Not[B]](a => s"!(${B.message(a)})")
  implicit def combinatorsOrError[A, B <: Refinement, C <: Refinement](implicit B: ErrorMessage[A, B], C: ErrorMessage[A, C]) = ErrorMessage[A, Combinators.Or[B, C]](a => s"!(${B.message(a)} || ${C.message(a)})")

  implicit def iterableEmptyError[A] = ErrorMessage[A, Iterable.Empty](a => s"${a} was non-empty")
  implicit def iterableMaxSizeError[A, B <: Int : ValueOf] = ErrorMessage[A, Iterable.MaxSize[B]](a => s"${a} contains more than ${valueOf[B]} elements")
  implicit def iterableMinSizeError[A, B <: Int : ValueOf] = ErrorMessage[A, Iterable.MinSize[B]](a => s"${a} contains less than ${valueOf[B]} elements")
}
