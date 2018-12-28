package nequi.posh
package sz

import scalaz._
import Scalaz._

object show {
  implicit def refinedShow[A, B <: Refined[A, _]](implicit A: Show[A]): Show[B] = A.contramap(_.unrefined)
}
