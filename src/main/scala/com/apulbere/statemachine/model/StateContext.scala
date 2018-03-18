package com.apulbere.statemachine.model

case class StateContext[S, E](
  source: State[S, E],
  target: Option[State[S, E]] = None,
  variable: Option[Any] = None,
  exception: Option[Exception] = None
)

object StateContext {
  def apply[S, E](source: State[S, E], target: State[S, E], variable: Any): StateContext[S, E] = {
    StateContext(source, Option(target), Option(variable))
  }

  def apply[S, E](source: State[S, E], variable: Any): StateContext[S, E] = {
    StateContext(source, None, Option(variable))
  }
}
