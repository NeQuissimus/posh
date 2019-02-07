package nequi.posh
package combinators

trait CombinatorRefinement extends Refinement

final case class Or[A <: Refinement, B <: Refinement]()  extends CombinatorRefinement
final case class And[A <: Refinement, B <: Refinement]() extends CombinatorRefinement
final case class Not[A <: Refinement]()                  extends CombinatorRefinement
final case class Xor[A <: Refinement, B <: Refinement]() extends CombinatorRefinement

trait CombinatorImplicits {
  type OneOf3[A <: Refinement, B <: Refinement, C <: Refinement]                  = A Xor B Xor C
  type OneOf4[A <: Refinement, B <: Refinement, C <: Refinement, D <: Refinement] = A Xor B Xor C Xor D
  type OneOf5[A <: Refinement, B <: Refinement, C <: Refinement, D <: Refinement, E <: Refinement] =
    A Xor B Xor C Xor D Xor E
  type OneOf6[A <: Refinement, B <: Refinement, C <: Refinement, D <: Refinement, E <: Refinement, F <: Refinement] =
    A Xor B Xor C Xor D Xor E Xor F
  type OneOf7[A <: Refinement,
              B <: Refinement,
              C <: Refinement,
              D <: Refinement,
              E <: Refinement,
              F <: Refinement,
              G <: Refinement] = A Xor B Xor C Xor D Xor E Xor F Xor G

  implicit def orRefined[A, B <: Refinement, C <: Refinement](a: A)(
    implicit B: A => Either[String, A Refined B],
    C: A => Either[String, A Refined C],
    E: ErrorMessage[A, Or[B, C]]
  ): Either[String, A Refined (B Or C)] = (B(a), C(a)) match {
    case (Left(_), Left(_)) => Left(E.message(a))
    case (_, _)             => Right(Refined[A, (B Or C)](a))
  }

  implicit def andRefined[A, B <: Refinement, C <: Refinement](a: A)(
    implicit B: A => Either[String, A Refined B],
    C: A => Either[String, A Refined C],
    E: ErrorMessage[A, And[B, C]]
  ): Either[String, A Refined (B And C)] = (B(a), C(a)) match {
    case (Right(_), Right(_)) => Right(Refined[A, (B And C)](a))
    case (Left(x), Right(_))  => Left(x)
    case (Right(_), Left(x))  => Left(x)
    case (Left(_), Left(_))   => Left(E.message(a))
  }

  implicit def notRefined[A, B <: Refinement](a: A)(implicit B: A => Either[String, A Refined B],
                                                    E: ErrorMessage[A, Not[B]]): Either[String, A Refined Not[B]] =
    B(a) match {
      case Left(_)  => Right(Refined[A, Not[B]](a))
      case Right(_) => Left(E.message(a))
    }

  implicit def xorRefined[A, B <: Refinement, C <: Refinement](a: A)(
    implicit B: A => Either[String, A Refined B],
    C: A => Either[String, A Refined C],
    E: ErrorMessage[A, Xor[B, C]]
  ): Either[String, A Refined (B Xor C)] = (B(a), C(a)) match {
    case (Left(_), Left(_))   => Left(E.message(a))
    case (Right(_), Right(_)) => Left(E.message(a))
    case (_, _)               => Right(Refined[A, (B Xor C)](a))
  }
}

trait CombinatorErrors {
  implicit def combinatorsAndError[A, B <: Refinement, C <: Refinement](implicit B: ErrorMessage[A, B],
                                                                        C: ErrorMessage[A, C]) =
    ErrorMessage[A, And[B, C]](a => s"!(${B.message(a)} && ${C.message(a)})")
  implicit def combinatorsNotError[A, B <: Refinement](implicit B: ErrorMessage[A, B]) =
    ErrorMessage[A, Not[B]](a => s"!(${B.message(a)})")
  implicit def combinatorsOrError[A, B <: Refinement, C <: Refinement](implicit B: ErrorMessage[A, B],
                                                                       C: ErrorMessage[A, C]) =
    ErrorMessage[A, Or[B, C]](a => s"!(${B.message(a)} || ${C.message(a)})")
  implicit def combinatorsXorError[A, B <: Refinement, C <: Refinement](implicit B: ErrorMessage[A, B],
                                                                        C: ErrorMessage[A, C]) =
    ErrorMessage[A, Xor[B, C]](
      a => s"!((${B.message(a)} && !${C.message(a)}) || (!${B.message(a)} && ${C.message(a)}))"
    )
}

object implicits extends CombinatorImplicits with CombinatorErrors
