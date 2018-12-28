package nequi.posh

import scalaz._
import Scalaz._

object implicits {
  implicit def refinedShow[A, B <: Refined[A, _]](implicit A: Show[A]): Show[B] = A.contramap(_.unrefined)
}
