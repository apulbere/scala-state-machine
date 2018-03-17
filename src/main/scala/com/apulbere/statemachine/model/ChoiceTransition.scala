package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

case class ChoiceTransition[S, E](
   source: S,
   choiceTargets: List[ChoiceTarget[S, E]],
   lastTarget: S,
   event: E,
   action: Option[Action[S, E]],
   errorAction: Option[Action[S, E]]
) extends Transition[S, E] {

  override def target(stateContext: StateContext[S, E]): S = {
    choiceTargets.find(_.guard.evaluate(stateContext)).map(_.target).getOrElse(lastTarget)
  }
}
