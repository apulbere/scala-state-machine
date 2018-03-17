package com.apulbere.statemachine

import com.apulbere.statemachine.model.StateContext

trait Guard[S] {
  def evaluate(stateContext: StateContext[S]): Boolean
}
