package com.apulbere.statemachine.builder

import com.apulbere.statemachine.Action
import com.apulbere.statemachine.model.Transition
import com.apulbere.statemachine.validator.{Condition, Validator}

abstract class TransitionBuilder[S, E](private val config: TransitionConfig[S, E]) {
  protected var source: S = _
  protected var event: E = _
  protected var action: Option[Action[S]] = None
  protected var errorAction: Option[Action[S]] = None

  def source(source: S): this.type = { this.source = source; this }

  def event(event: E): this.type = { this.event = event; this }

  def action(action: Action[S]): this.type = { this.action = Option(action); this }

  def action(action: Action[S], errorAction: Action[S]): this.type = {
    this.action = Option(action)
    this.errorAction = Option(errorAction)
    this
  }

  def and(): TransitionConfig[S, E] = config

  def end(): StateMachineBuilder[S, E] = config.end()

  def validator(): Validator = {
    val conditions = List(
      Condition(() => source != null, "source is not defined"),
      Condition(() => event != null, "event is not defined")
    )
    Validator("transition", conditions)
  }

  private[builder] def build(): Transition[S, E]
}
