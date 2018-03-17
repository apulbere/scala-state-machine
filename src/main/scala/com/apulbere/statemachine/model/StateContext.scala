package com.apulbere.statemachine.model

case class StateContext[S](state: S, exception: Option[Exception] = None)
