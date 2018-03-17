package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ValidatorSpec extends FlatSpec with BeforeAndAfter with Matchers {

  "the state machine builder" should "throw an exception with a detailed message " +
    "when building an invalid choice transition" in {
    val expectedMessage =
      """
        |Invalid state machine: state machine
        |	initial state is not defined
        |	transition config
        |		choice transition
        |			last is not defined
        |			source is not defined
        |			event is not defined
        |			choice target
        |				target is not defined
        |				guard is not defined
      """.stripMargin.trim

    try {
      StateMachineBuilder()
        .configureTransitions()
          .withChoiceTransition()
            .choice(null, null)
          .end()
        .build()
    } catch {
      case e: IllegalStateException => e.getMessage should equal(expectedMessage)
    }
  }

  it should "throw an exception with a detailed message when " +
    "building an invalid direct transition" in {
    val expectedMessage =
      """
        |Invalid state machine: state machine
        |	transition config
        |		direct transition
        |			target is not defined
        |			source is not defined
        |			event is not defined
      """.stripMargin.trim

    try {
      StateMachineBuilder()
        .initialState("S1")
        .configureTransitions()
          .withTransition()
          .end()
        .build()
    } catch {
      case e: IllegalStateException => e.getMessage should equal(expectedMessage)
    }
  }
}
