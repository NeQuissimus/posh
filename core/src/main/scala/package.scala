package nequi

package object posh {
  object implicits
      extends combinators.CombinatorErrors
      with combinators.CombinatorImplicits
      with iterable.IterableErrors
      with iterable.IterableImplicits
}
