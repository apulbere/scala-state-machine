package com.apulbere.statemachine.impl

import com.apulbere.statemachine.model.{Transition, StateContext}
import com.apulbere.statemachine.{Action, StateMachine}

import scala.util.{Failure, Success, Try}

class DefaultStateMachine[S, E](private var stateContext: StateContext[S, E],
                                private val transitions: List[Transition[S, E]],
                                private val stateListener: Option[Action[S, E]])
  extends StateMachine[S, E] {

  override def state(): S = stateContext.state

  override def sendEvent(event: E, variable: Any = None): Unit = {
    transitions.find(transitionMatches(_, event)).foreach(executeTransition(_, variable))
  }

  private def transitionMatches(transition: Transition[S, E], event: E): Boolean = {
      stateContext.state == transition.source && event == transition.event
  }

  private def executeTransition(transition: Transition[S, E], variable: Any): Unit = {
    val stateContextBeforeTargetCheck = stateContext.copy(
      event = Option(transition.event),
      variable = Option(variable)
    )
    val targetState = transition.target(stateContextBeforeTargetCheck)
    val newStateContext = stateContextBeforeTargetCheck.copy(state = targetState)

    Try(transition.action.foreach(_.execute(newStateContext))) match {
      case Success(_) => onSuccessTransition(newStateContext)
      case Failure(exception: Exception) => onFailureTransition(transition, exception)
      case Failure(otherException) => throw otherException
    }
  }

  private def onSuccessTransition(newStateContext: StateContext[S, E]): Unit = {
    this.stateContext = newStateContext
    stateListener.foreach(_.execute(newStateContext))
  }

  private def onFailureTransition(transition: Transition[S, E], exception: Exception): Unit = {
    val stateContextWithException = stateContext.copy(exception = Option(exception))
    transition.errorAction.foreach(_.execute(stateContextWithException))
  }
}

object DefaultStateMachine {
  def apply[S, E](initialState: S, transitions: List[Transition[S, E]], stateListener: Action[S, E]): StateMachine[S, E] = {
    val stateContext = new StateContext[S, E](initialState)
    new DefaultStateMachine[S, E](stateContext, transitions, Option(stateListener))
  }
}