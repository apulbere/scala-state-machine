package com.apulbere.statemachine.model

import com.apulbere.statemachine.Action

class Transition[S, E] (val source: S,
                        val target: S,
                        val event: E,
                        val action: Option[Action[S]],
                        val errorAction: Option[Action[S]])
