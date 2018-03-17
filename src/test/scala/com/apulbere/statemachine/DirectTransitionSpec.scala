package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class DirectTransitionSpec extends FlatSpec with BeforeAndAfter with Matchers {
  var stateMachine: StateMachine[String, String] = _

  before {
    stateMachine = StateMachineBuilder()
        .initialState("S1")
        .configureTransitions()
          .withTransition()
            .source("S1")
            .target("S2")
            .event("E1")
          .and()
          .withTransition()
            .source("S2")
            .target("S3")
            .event("E4")
          .and()
          .withTransition()
            .source("S1")
            .target("S3")
            .event("E2")
        .end()
      .build()
  }

  "the state machine" should "acquire S2 state when E1 is sent" in {
    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S2")
  }

  it should "acquire S3 state when E4 is sent" in {
    stateMachine.sendEvent("E2")
    stateMachine.state() should equal("S3")
  }

  it should "acquire S3 state after 2 events are sent" in {
    stateMachine.sendEvent("E1")
    stateMachine.sendEvent("E4")
    stateMachine.state() should equal("S3")
  }

  it should "preserve state for an unknown event" in {
    stateMachine.sendEvent("EX")
    stateMachine.state() should equal("S1")
  }
}
