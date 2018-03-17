package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

case class DirectTransition[S, E](
   source: S,
   target: S,
   event: E,
   action: Option[Action[S, E]],
   errorAction: Option[Action[S, E]]
) extends Transition[S, E] {

  override def target(stateContext: StateContext[S, E]): S = target
}