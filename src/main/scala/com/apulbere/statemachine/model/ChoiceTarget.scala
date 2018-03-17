package com.apulbere.statemachine.model

import com.apulbere.statemachine.Guard
import com.apulbere.statemachine.validator.{Condition, Validator}

case class ChoiceTarget[S](target: S, guard: Guard[S]) {

  def validator(): Validator = {
    val conditions = List(
      Condition(() => target != null, "target is not defined"),
      Condition(() => guard != null, "guard is not defined")
    )
    Validator("choice target", conditions)
  }
}

