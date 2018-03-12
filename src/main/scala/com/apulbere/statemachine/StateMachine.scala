package com.apulbere.statemachine

trait StateMachine[S, E] {
  def state(): S
  def sendEvent(event: E): Unit
}
