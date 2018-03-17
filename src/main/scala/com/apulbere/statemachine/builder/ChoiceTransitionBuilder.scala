package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Guard
import com.apulbere.statemachine.model.{ChoiceTarget, ChoiceTransition, Transition}
import com.apulbere.statemachine.validator.{Condition, Validator}

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

  override def validator(): Validator = {
    val conditions = List(Condition(() => lastTarget != null, "last is not defined"))
    val choiceTargetValidators = choiceTargets.map(_.validator())
    Validator("choice transition", conditions, choiceTargetValidators) + super.validator()
  }
}
