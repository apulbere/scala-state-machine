package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TransitionFlowSpec extends FlatSpec with BeforeAndAfter with Matchers{
  var stateMachine: StateMachine[String, String] = _

  before {
    stateMachine = new StateMachineBuilder[String, String]()
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

  "the state machine" should "acquire S2 status" in {
    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S2")
  }

  "the state machine" should "acquire S3 status" in {
    stateMachine.sendEvent("E2")
    stateMachine.state() should equal("S3")
  }

  "the state machine" should "acquire S3 status after 2 events" in {
    stateMachine.sendEvent("E1")
    stateMachine.sendEvent("E4")
    stateMachine.state() should equal("S3")
  }

  "the state machine" should "preserve for an unknown event" in {
    stateMachine.sendEvent("EX")
    stateMachine.state() should equal("S1")
  }
}
