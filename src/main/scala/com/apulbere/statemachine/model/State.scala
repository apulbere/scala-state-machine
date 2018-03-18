package com.apulbere.statemachine.model

case class State[S, E](state: S, event: Option[E] = None)

object State {
  def apply[S, E](state: S, event: E): State[S, E] = State(state, Option(event))
}
