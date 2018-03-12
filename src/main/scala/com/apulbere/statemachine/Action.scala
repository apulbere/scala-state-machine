package com.apulbere.statemachine

import com.apulbere.statemachine.model.StateContext

trait Action[S] {
  def execute(stateContext: StateContext[S]): Unit
}
