package com.apulbere.statemachine

import com.apulbere.statemachine.builder.StateMachineBuilder
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class TransitionActionSpec extends FlatSpec with BeforeAndAfter with Matchers with MockFactory {

  "action" should "be executed when the transition is triggered" in {
    val e1EventAction = stub[Action[String]]
    val stateMachine = new StateMachineBuilder[String, String]()
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
    val e1EventAction = stub[Action[String]]
    val stateMachine = new StateMachineBuilder[String, String]()
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

    val e1EventAction: Action[String] = _ => throw expectedException

    val e1EventErrorAction: Action[String] = statecontext => {
      statecontext.exception.get should equal(expectedException)
    }

    val stateMachine = new StateMachineBuilder[String, String]()
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
