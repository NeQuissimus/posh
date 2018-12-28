package nequi.posh

trait Refinement

final case class Refined[A, B <: Refinement] private(val unrefined: A) extends AnyVal

final class RefinementException(val s: String) extends RuntimeException(s)

object Refined {
  def unsafeCoerce[A, B <: Refinement](a: A)(implicit f: A => Either[String, A Refined B]) = f(a) match {
    case Left(err) => throw new RefinementException(err)
    case Right(x) => x
  }
}
