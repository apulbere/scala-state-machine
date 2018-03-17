package com.apulbere.statemachine.builder

import com.apulbere.statemachine.model.Transition

class TransitionConfig[S, E] (val stateMachineBuilder: StateMachineBuilder[S, E]) {
  private var transitionBuilders = List[TransitionBuilder[S, E]]()

  private[builder] def build(): List[Transition[S, E]] = transitionBuilders.map(_.build())

  private[builder] def isValid(): Boolean = transitionBuilders.forall(_.isValid())

  def withTransition(): DirectTransitionBuilder[S, E] = {
    val transitionBuilder = new DirectTransitionBuilder[S, E](this)
    transitionBuilders = transitionBuilder :: transitionBuilders
    transitionBuilder
  }

  def withChoiceTransition(): ChoiceTransitionBuilder[S, E] = {
    val transitionBuilder = new ChoiceTransitionBuilder[S, E](this)
    transitionBuilders = transitionBuilder :: transitionBuilders
    transitionBuilder
  }

  def end(): StateMachineBuilder[S, E] = stateMachineBuilder
}
