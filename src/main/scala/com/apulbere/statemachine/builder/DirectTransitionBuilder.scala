package com.apulbere.statemachine.builder

import com.apulbere.statemachine.model.{DirectTransition, Transition}
import com.apulbere.statemachine.validator.{Condition, Validator}

class DirectTransitionBuilder[S, E](config: TransitionConfig[S, E]) extends TransitionBuilder[S, E](config) {
  private var target: S = _

  def target(target: S): this.type = { this.target = target; this }

  override private[builder] def build(): Transition[S, E] = {
    DirectTransition(source, target, event, action, errorAction)
  }

  override def validator(): Validator = {
    val conditions = List(Condition(() => target != null, "target is not defined"))
    Validator("direct transition", conditions) + super.validator()
  }
}
