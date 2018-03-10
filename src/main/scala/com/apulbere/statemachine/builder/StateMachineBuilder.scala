package com.apulbere.statemachine.builder

import com.apulbere.statemachine.StateMachine
import com.apulbere.statemachine.impl.DefaultStateMachine
import com.apulbere.statemachine.model.StateContext

class StateMachineBuilder[S, E] {
  private var initialState: S = _
  private val transitionConfig = new TransitionConfig[S, E](this)

  def configureTransitions(): TransitionConfig[S, E] = transitionConfig
  def withInitialState(initialState: S): StateMachineBuilder[S, E] = { this.initialState = initialState; this }
  def build(): StateMachine[S, E] = {
    new DefaultStateMachine[S, E](new StateContext[S](initialState), transitionConfig.build)
  }
}
