package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

case class DirectTransition[S, E](
   source: S,
   target: S,
   event: E,
   action: Option[Action[S]],
   errorAction: Option[Action[S]]
) extends Transition[S, E] {

  override def target(stateContext: StateContext[S]): S = target
}