package com.apulbere.statemachine.builder

import com.apulbere.statemachine.model.Transition

class TransitionBuilder[S, E] (val transitionConfig: TransitionConfig[S, E]) {
  private var source: S = _
  private var target: S = _
  private var event: E = _

  def source(source: S): TransitionBuilder[S, E] = { this.source = source; this }
  def target(target: S): TransitionBuilder[S, E] = { this.target = target; this }
  def event(event: E): TransitionBuilder[S, E] = { this.event = event; this }

  def and(): TransitionConfig[S, E] = transitionConfig

  def end(): StateMachineBuilder[S, E] = transitionConfig.end

  private[builder] def build = new Transition(source, target, event)
}
