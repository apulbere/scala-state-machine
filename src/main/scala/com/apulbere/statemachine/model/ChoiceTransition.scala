package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

case class ChoiceTransition[S, E](
   source: S,
   choiceTargets: List[ChoiceTarget[S]],
   lastTarget: S,
   event: E,
   action: Option[Action[S]],
   errorAction: Option[Action[S]]
) extends Transition[S, E] {

  override def target(stateContext: StateContext[S]): S = {
    choiceTargets.find(_.guard.evaluate(stateContext)).map(_.target).getOrElse(lastTarget)
  }
}
