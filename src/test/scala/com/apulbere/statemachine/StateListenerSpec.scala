package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import com.apulbere.statemachine.model.State
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class StateListenerSpec extends FlatSpec with Matchers with MockFactory {

  "state listener" should "be called on state change" in {
    val stateListener: Action[String, String] = stateContext => {
      stateContext.target should equal(Option(State("S2", "E1")))
    }

    val stateMachine = StateMachineBuilder[String, String]()
      .initialState("S1")
      .stateListener(stateListener)
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
        .end()
      .build()

    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S2")
  }

  it should "not be called when state is not changed" in {
    val stateListener = stub[Action[String, String]]

    val stateMachine = StateMachineBuilder[String, String]()
      .initialState("S1")
      .stateListener(stateListener)
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
        .end()
      .build()

    stateMachine.sendEvent("EX")

    (stateListener.execute _).verify(*).never()
  }
}
