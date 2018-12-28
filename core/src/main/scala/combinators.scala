package nequi.posh

object Combinators {
  trait CombinatorRefinement extends Refinement

  final case class Or[A <: Refinement, B <: Refinement]() extends CombinatorRefinement
  final case class And[A <: Refinement, B <: Refinement]() extends CombinatorRefinement
  final case class Not[A <: Refinement]() extends CombinatorRefinement

  implicit def orRefined[A, B <: Refinement, C <: Refinement](a: A)(implicit B: A => Either[String, A Refined B], C: A => Either[String, A Refined C], E: ErrorMessage[A, Or[B, C]]): Either[String, A Refined (B Or C)] = (B(a), C(a)) match {
    case (Left(_), Left(_)) => Left(E.message(a))
    case (_, _) => Right(Refined[A, (B Or C)](a))
  }

  implicit def andRefined[A, B <: Refinement, C <: Refinement](a: A)(implicit B: A => Either[String, A Refined B], C: A => Either[String, A Refined C], E: ErrorMessage[A, And[B, C]]): Either[String, A Refined (B And C)] = (B(a), C(a)) match {
    case (Right(_), Right(_)) => Right(Refined[A, (B And C)](a))
    case (Left(x), Right(_)) => Left(x)
    case (Right(_), Left(x)) => Left(x)
    case (Left(_), Left(_)) => Left(E.message(a))
  }

  implicit def notRefined[A, B <: Refinement](a: A)(implicit B: A => Either[String, A Refined B], E: ErrorMessage[A, Not[B]]): Either[String, A Refined Not[B]] = B(a) match {
    case Left(_) => Right(Refined[A, Not[B]](a))
    case Right(_) => Left(E.message(a))
  }
}
