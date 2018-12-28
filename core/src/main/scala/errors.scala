package nequi.posh

trait ErrorMessage[A, B <: Refinement] {
  def message(a: A): String
}

object ErrorMessage {
  def apply[A, B <: Refinement](f: A => String) = new ErrorMessage[A, B] {
    def message(a: A) = f(a)
  }
}
