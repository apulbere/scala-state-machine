package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Guard
import com.apulbere.statemachine.model.{ChoiceTransition, Transition}
import com.apulbere.statemachine.validator.{Condition, Validator}

class ChoiceTransitionBuilder[S, E](config: TransitionConfig[S, E]) extends TransitionBuilder[S, E](config) {
  private var choiceTargetBuilders = List.empty[ChoiceTargetBuilder[S]]
  private var lastTarget: S = _

  def choice(target: S, guard: Guard[S]): this.type = {
    choiceTargetBuilders = choiceTargetBuilders :+ new ChoiceTargetBuilder(target, guard)
    this
  }

  def last(target: S): this.type = { this.lastTarget = target; this }

  override private[builder] def build(): Transition[S, E] = {
    val choiceTargets = choiceTargetBuilders.map(_.build())
    ChoiceTransition(source, choiceTargets, lastTarget, event, action, errorAction)
  }

  override def validator(): Validator = {
    val conditions = List(Condition(() => lastTarget != null, "last is not defined"))
    val choiceTargetValidators = choiceTargetBuilders.map(_.validator())
    Validator("choice transition", conditions, choiceTargetValidators) + super.validator()
  }
}
