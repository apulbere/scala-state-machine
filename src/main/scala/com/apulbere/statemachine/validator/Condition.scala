package com.apulbere.statemachine.validator

case class Condition(validationFunction: () => Boolean, message: String)