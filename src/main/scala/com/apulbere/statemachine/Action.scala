package com.apulbere.statemachine

import com.apulbere.statemachine.model.StateContext

trait Action[S, E] {
  def execute(stateContext: StateContext[S, E]): Unit
}
