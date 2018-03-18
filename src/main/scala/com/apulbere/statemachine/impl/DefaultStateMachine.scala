package com.apulbere.statemachine.impl

import com.apulbere.statemachine.model.{State, StateContext, Transition}
import com.apulbere.statemachine.{Action, StateMachine}

import scala.util.{Failure, Success, Try}

class DefaultStateMachine[S, E](private var currentState: State[S, E],
                                private val transitions: List[Transition[S, E]],
                                private val stateListener: Option[Action[S, E]])
  extends StateMachine[S, E] {

  override def state(): S = currentState.state

  override def sendEvent(event: E, variable: Any = None): Unit = {
    transitions.find(transitionMatches(_, event)).foreach(executeTransition(_, variable))
  }

  private def transitionMatches(transition: Transition[S, E], event: E): Boolean = {
      currentState.state == transition.source && event == transition.event
  }

  private def executeTransition(transition: Transition[S, E], variable: Any): Unit = {
    val targetState = transition.target(StateContext(currentState, variable))
    val stateContext = StateContext(currentState, State(targetState, transition.event), variable)

    Try(transition.action.foreach(_.execute(stateContext))) match {
      case Success(_) => onSuccessTransition(stateContext)
      case Failure(exception: Exception) => {
        onFailureTransition(transition, stateContext.copy(exception = Option(exception)))
      }
      case Failure(otherException) => throw otherException
    }
  }

  private def onSuccessTransition(stateContext: StateContext[S, E]): Unit = {
    stateContext.target.foreach(targetState => {
      this.currentState = targetState
      stateListener.foreach(_.execute(stateContext))
    })
  }

  private def onFailureTransition(transition: Transition[S, E], stateContext: StateContext[S, E]): Unit = {
    transition.errorAction.foreach(_.execute(stateContext))
  }
}

object DefaultStateMachine {
  def apply[S, E](initialState: S, transitions: List[Transition[S, E]], stateListener: Action[S, E]): StateMachine[S, E] = {
    new DefaultStateMachine[S, E](State(initialState), transitions, Option(stateListener))
  }
}