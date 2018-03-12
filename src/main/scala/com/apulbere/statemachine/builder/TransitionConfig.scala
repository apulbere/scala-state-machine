package com.apulbere.statemachine.builder

class TransitionConfig[S, E] (val stateMachineBuilder: StateMachineBuilder[S, E]) {
  private var transitionBuilders = List[TransitionBuilder[S, E]]()

  private[builder] def build() = transitionBuilders.map(_.build())

  def withTransition(): TransitionBuilder[S, E] = {
    val transitionBuilder = new TransitionBuilder[S, E](this)
    transitionBuilders = transitionBuilder :: transitionBuilders
    transitionBuilder
  }

  def end(): StateMachineBuilder[S, E] = stateMachineBuilder
}
