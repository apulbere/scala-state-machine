package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class StateListenerSpec extends FlatSpec with BeforeAndAfter with Matchers with MockFactory {
  var stateMachine: StateMachine[String, String] = _
  var stateListener: Action[String] = _

  before {
    stateListener = stub[Action[String]]

    stateMachine = new StateMachineBuilder[String, String]()
      .initialState("S1")
      .stateListener(stateListener)
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
        .end()
      .build()
  }

  "state listener" should "be called on state change" in {
    stateMachine.sendEvent("E1")

    (stateListener.execute _).verify(*).once()
  }

  it should "not be called when state is not changed" in {
    stateMachine.sendEvent("EX")

    (stateListener.execute _).verify(*).never()
  }
}
