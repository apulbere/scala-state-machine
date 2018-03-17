package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ChoiceTransitionSpec extends FlatSpec with BeforeAndAfter with Matchers {

  "the state machine" should "acquire S2 state when first guard returns true" in {
    val stateMachine = new StateMachineBuilder[String, String]()
        .initialState("S1")
        .configureTransitions()
          .withChoiceTransition()
            .source("S1")
            .choice("S2", _ => true)
            .choice("S3", _ => true)
            .last("S4")
            .event("E1")
          .end()
        .build()

    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S2")
  }

  it should "acquire S3 state when first guard returns false and second true" in {
    val stateMachine = new StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withChoiceTransition()
          .source("S1")
          .choice("S2", _ => false)
          .choice("S3", _ => true)
          .last("S4")
          .event("E1")
        .end()
      .build()

    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S3")
  }

  it should "acquire S4 state when both guards return false" in {
    val stateMachine = new StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withChoiceTransition()
          .source("S1")
          .choice("S2", _ => false)
          .choice("S3", _ => false)
          .last("S4")
          .event("E1")
        .end()
      .build()

    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S4")
  }

  it should "acquire S4 state when first guard returns false and second choice is not defined" in {
    val stateMachine = new StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withChoiceTransition()
          .source("S1")
          .choice("S2", _ => false)
          .last("S4")
          .event("E1")
        .end()
      .build()

    stateMachine.sendEvent("E1")
    stateMachine.state() should equal("S4")
  }
}
