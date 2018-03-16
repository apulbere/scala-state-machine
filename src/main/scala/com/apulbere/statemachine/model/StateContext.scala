package com.apulbere.statemachine.model

case class StateContext[S](state: S, exception: Option[Exception]) {
  def this(state: S) = this(state, None)
}
