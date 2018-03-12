package com.apulbere.statemachine.builder

import com.apulbere.statemachine.impl.AbstractStateMachine
import com.apulbere.statemachine.model.StateContext
import com.apulbere.statemachine.{Action, StateMachine}

class StateMachineBuilder[S, E] {
  private var initialState: S = _
  private val transitionConfig = new TransitionConfig[S, E](this)
  private var globalListener: Option[Action[S]] = None

  def configureTransitions(): TransitionConfig[S, E] = transitionConfig
  def globalListener(globalListener: Action[S]): this.type = { this.globalListener = Option(globalListener); this }
  def initialState(initialState: S): this.type = { this.initialState = initialState; this }
  def build(): StateMachine[S, E] = {
    new AbstractStateMachine[S, E](new StateContext[S](initialState), transitionConfig.build(), globalListener)
  }
}
