package nequi.posh
package iterable

trait IterableRefinement extends Refinement

final case class Empty()             extends IterableRefinement
final case class MinSize[A <: Int]() extends IterableRefinement
final case class MaxSize[A <: Int]() extends IterableRefinement
final case class Size[A <: Int]()    extends IterableRefinement

trait IterableImplicits {
  implicit def emptyRefined[A](a: A)(implicit A: A => Iterable[_],
                                     E: ErrorMessage[A, Empty]): Either[String, A Refined Empty] = a match {
    case x if x.isEmpty => Right(Refined[A, Empty](a))
    case x              => Left(E.message(x))
  }

  implicit def minSizeRefined[A, B <: Int](
    a: A
  )(implicit A: A => Iterable[_], b: ValueOf[B], E: ErrorMessage[A, MinSize[B]]): Either[String, A Refined MinSize[B]] =
    a match {
      case x if x.size >= valueOf[B] => Right(Refined[A, MinSize[B]](a))
      case x                         => Left(E.message(x))
    }

  implicit def maxSizeRefined[A, B <: Int](
    a: A
  )(implicit A: A => Iterable[_], b: ValueOf[B], E: ErrorMessage[A, MaxSize[B]]): Either[String, A Refined MaxSize[B]] =
    a match {
      case x if x.size <= valueOf[B] => Right(Refined[A, MaxSize[B]](a))
      case x                         => Left(E.message(x))
    }

  implicit def sizeRefined[A, B <: Int](
    a: A
  )(implicit A: A => Iterable[_], b: ValueOf[B], E: ErrorMessage[A, MaxSize[B]]): Either[String, A Refined Size[B]] =
    a match {
      case x if x.size == valueOf[B] => Right(Refined[A, Size[B]](a))
      case x                         => Left(E.message(x))
    }
}

trait IterableErrors {
  implicit def iterableEmptyError[A] = ErrorMessage[A, Empty](a => s"${a} was non-empty")
  implicit def iterableMaxSizeError[A, B <: Int: ValueOf] =
    ErrorMessage[A, MaxSize[B]](a => s"${a} contains more than ${valueOf[B]} elements")
  implicit def iterableMinSizeError[A, B <: Int: ValueOf] =
    ErrorMessage[A, MinSize[B]](a => s"${a} contains less than ${valueOf[B]} elements")
  implicit def iterableSizeError[A, B <: Int: ValueOf] =
    ErrorMessage[A, Size[B]](a => s"${a} does not contain ${valueOf[B]} elements")
}

object implicits extends IterableImplicits with IterableErrors
