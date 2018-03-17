package com.apulbere.statemachine.builder

import com.apulbere.statemachine.impl.DefaultStateMachine
import com.apulbere.statemachine.validator.{Condition, Validator}
import com.apulbere.statemachine.{Action, StateMachine}

class StateMachineBuilder[S, E] {
  private var initialState: S = _
  private val transitionConfig = new TransitionConfig[S, E](this)
  private var stateListener: Action[S] = _

  def configureTransitions(): TransitionConfig[S, E] = transitionConfig

  def stateListener(stateListener: Action[S]): this.type = { this.stateListener = stateListener; this }

  def initialState(initialState: S): this.type = { this.initialState = initialState; this }

  def validator(): Validator = {
    val conditions = List(
      Condition(() => initialState != null, "initial state is not defined")
    )
    Validator("state machine", conditions, List(transitionConfig.validator()))
  }

  def build(): StateMachine[S, E] = {
    val validatorResult = validator().validate()
    if(validatorResult.isValid) {
      DefaultStateMachine[S, E](initialState, transitionConfig.build(), stateListener)
    } else {
      throw new IllegalStateException("Invalid state machine: " + validatorResult.message)
    }
  }
}

object StateMachineBuilder {
  def apply[S, E](): StateMachineBuilder[S, E] = new StateMachineBuilder[S, E]()
}
