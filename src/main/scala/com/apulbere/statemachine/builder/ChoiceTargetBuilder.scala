package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Guard
import com.apulbere.statemachine.model.ChoiceTarget
import com.apulbere.statemachine.validator.{Condition, Validator}

class ChoiceTargetBuilder[S](target: S, guard: Guard[S]) {

  def validator(): Validator = {
    val conditions = List(
      Condition(() => target != null, "target is not defined"),
      Condition(() => guard != null, "guard is not defined")
    )
    Validator("choice target", conditions)
  }

  def build(): ChoiceTarget[S] = ChoiceTarget(target, guard)
}
