package com.apulbere.statemachine.model

class Transition[S, E] (val source: S, val target: S, val event: E)
