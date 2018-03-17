package com.apulbere.statemachine.model

case class StateContext[S, E](
  state: S,
  event: Option[E] = None,
  exception: Option[Exception] = None,
  variable: Any = None
)
