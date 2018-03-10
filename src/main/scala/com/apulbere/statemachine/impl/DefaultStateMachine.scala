package com.apulbere.statemachine.impl

import com.apulbere.statemachine.StateMachine
import com.apulbere.statemachine.model.{StateContext, Transition}

class DefaultStateMachine[S, E] (private val stateContext: StateContext[S],
                                  private val transitions: List[Transition[S, E]])
  extends StateMachine[S, E] {

  override def getState(): S = stateContext.state

  override def sendEvent(event: E): Unit = {
    transitions.find(t => stateContext.state == t.source && event == t.event)
               .foreach(t => stateContext.state = t.target)
  }
}
