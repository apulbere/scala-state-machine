package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class GlobalListenerSpec extends FlatSpec with BeforeAndAfter with Matchers with MockFactory {
  var stateMachine: StateMachine[String, String] = _
  var globalListener: Action[String] = _

  before {
    globalListener = stub[Action[String]]

    stateMachine = new StateMachineBuilder[String, String]()
      .initialState("S1")
      .globalListener(globalListener)
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
        .end()
      .build()
  }

  "global listener" should "be called on status change" in {
    stateMachine.sendEvent("E1")

    (globalListener.execute _).verify(*).once()
  }

  it should "not be called when status is not changed" in {
    stateMachine.sendEvent("EX")

    (globalListener.execute _).verify(*).never()
  }
}
