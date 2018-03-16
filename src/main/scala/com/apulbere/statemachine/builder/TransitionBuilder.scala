package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Action
import com.apulbere.statemachine.model.Transition

class TransitionBuilder[S, E] (val transitionConfig: TransitionConfig[S, E]) {
  private var source: S = _
  private var target: S = _
  private var event: E = _
  private var action: Option[Action[S]] = None
  private var errorAction: Option[Action[S]] = None

  def source(source: S): this.type = { this.source = source; this }

  def target(target: S): this.type = { this.target = target; this }

  def event(event: E): this.type = { this.event = event; this }

  def action(action: Action[S]): this.type = { this.action = Option(action); this }

  def action(action: Action[S], errorAction: Action[S]): this.type = {
    this.action = Option(action)
    this.errorAction = Option(errorAction)
    this
  }

  def and(): TransitionConfig[S, E] = transitionConfig

  def end(): StateMachineBuilder[S, E] = transitionConfig.end()

  private[builder] def build() = new Transition(source, target, event, action, errorAction)
}
