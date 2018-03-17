package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

trait Transition[S, E] {
  def target(stateContext: StateContext[S, E]): S
  def source: S
  def event: E
  def action: Option[Action[S, E]]
  def errorAction: Option[Action[S, E]]
}
