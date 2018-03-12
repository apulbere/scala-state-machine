package com.apulbere.statemachine.impl

import com.apulbere.statemachine.model.{StateContext, Transition}
import com.apulbere.statemachine.{Action, StateMachine}

class AbstractStateMachine[S, E](private val stateContext: StateContext[S],
                                 private val transitions: List[Transition[S, E]],
                                 private val globalListener: Option[Action[S]])
  extends StateMachine[S, E] {

  override def state(): S = stateContext.state

  override def sendEvent(event: E): Unit = {
    transitions.find(t => stateContext.state == t.source && event == t.event)
      .foreach(t => {
        stateContext.state = t.target
        globalListener.foreach(_.execute(stateContext))
      })
  }
}
