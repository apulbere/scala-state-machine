package com.apulbere.statemachine

trait StateMachine[S, E] {
  def getState(): S
  def sendEvent(event: E): Unit
}
