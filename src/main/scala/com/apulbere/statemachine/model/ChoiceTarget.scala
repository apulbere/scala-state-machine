package com.apulbere.statemachine.model

import com.apulbere.statemachine.Guard

case class ChoiceTarget[S, E](target: S, guard: Guard[S, E])

