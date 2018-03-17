package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Guard
import com.apulbere.statemachine.model.{ChoiceTarget, ChoiceTransition, Transition}

class ChoiceTransitionBuilder[S, E](config: TransitionConfig[S, E]) extends TransitionBuilder[S, E](config) {
  private var choiceTargets = List.empty[ChoiceTarget[S]]
  private var lastTarget: S = _

  def choice(target: S, guard: Guard[S]): this.type = {
    choiceTargets = choiceTargets :+ ChoiceTarget(target, guard)
    this
  }

  def last(target: S): this.type = { this.lastTarget = target; this }

  override private[builder] def build(): Transition[S, E] = {
    ChoiceTransition(source, choiceTargets, lastTarget, event, action, errorAction)
  }

  override private[builder] def isValid(): Boolean = super.isValid() && isChoiceTargetValid() && lastTarget != null

  private def isChoiceTargetValid() = choiceTargets.forall(choiceTarget => choiceTarget.target != null && choiceTarget.guard != null)
}
