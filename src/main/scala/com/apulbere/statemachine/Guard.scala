package com.apulbere.statemachine

import com.apulbere.statemachine.model.StateContext

trait Guard[S, E] {
  def evaluate(stateContext: StateContext[S, E]): Boolean
}
