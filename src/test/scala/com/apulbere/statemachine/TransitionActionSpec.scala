package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import com.apulbere.statemachine.model.State
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class TransitionActionSpec extends FlatSpec with Matchers with MockFactory {

  "action" should "be executed when a transition is triggered" in {
    val e1EventAction = stub[Action[String, String]]
    val stateMachine = StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
          .action(e1EventAction)
        .end()
      .build()

    stateMachine.sendEvent("E1")

    (e1EventAction.execute _).verify(*).once()
  }

  it should "not be executed when a transition without action is triggered" in {
    val e1EventAction = stub[Action[String, String]]
    val stateMachine = StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
          .action(e1EventAction)
        .and()
        .withTransition()
          .source("S1")
          .target("S3")
          .event("E2")
        .end()
      .build()

    stateMachine.sendEvent("E2")

    (e1EventAction.execute _).verify(*).never()
  }

  "error action" should "be executed when an error occurs during transition" in {
    val expectedException = new Exception("dummy")

    val e1EventAction: Action[String, String] = _ => throw expectedException

    val e1EventErrorAction: Action[String, String] = stateContext => {
      stateContext.source should equal(State("S1"))
      stateContext.target should equal(Option(State("S2", "E1")))
      stateContext.exception should equal(Some(expectedException))
    }

    val stateMachine = StateMachineBuilder[String, String]()
      .initialState("S1")
      .configureTransitions()
        .withTransition()
          .source("S1")
          .target("S2")
          .event("E1")
          .action(e1EventAction, e1EventErrorAction)
        .end()
      .build()

    stateMachine.sendEvent("E1")

    stateMachine.state() should equal("S1")
  }
}
