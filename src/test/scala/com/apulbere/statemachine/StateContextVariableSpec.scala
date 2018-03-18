package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalatest.{FlatSpec, Matchers}

class StateContextVariableSpec extends FlatSpec with Matchers {

  "the state machine" should "acquire S2 due to 'variableIsBiggerThanTen' guard" in {

    val variableIsBiggerThanTen: Guard[String, String] = stateContext => {
      stateContext.variable match {
        case Some(value: BigDecimal) => value > BigDecimal(10)
        case _ => false
      }
    }

    val stateMachine = StateMachineBuilder[String, String]()
        .initialState("S1")
        .configureTransitions()
          .withChoiceTransition()
            .source("S1")
            .choice("S2", variableIsBiggerThanTen)
            .last("S4")
            .event("E1")
          .end()
        .build()

    stateMachine.sendEvent("E1", BigDecimal(11))
    stateMachine.state() should equal("S2")
  }

  "the state machine" should "forget variable on next transition" in {

    val variableIsBiggerThanTen: Guard[String, String] = stateContext => {
      stateContext.variable match {
        case Some(value: BigDecimal) => value > BigDecimal(10)
        case _ => false
      }
    }

    val stateMachine = StateMachineBuilder[String, String]()
        .initialState("S1")
        .configureTransitions()
          .withChoiceTransition()
            .source("S1")
            .choice("S2", variableIsBiggerThanTen)
            .last("S4")
            .event("E1")
          .and()
          .withChoiceTransition()
            .source("S2")
            .choice("S3", variableIsBiggerThanTen)
            .last("S5")
            .event("E2")
          .end()
        .build()

    stateMachine.sendEvent("E1", BigDecimal(11))
    stateMachine.state() should equal("S2")

    stateMachine.sendEvent("E2")
    stateMachine.state() should equal("S5")
  }
}
