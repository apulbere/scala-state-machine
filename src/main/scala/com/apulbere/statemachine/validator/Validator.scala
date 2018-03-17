package com.apulbere.statemachine.validator

case class Validator(
  source: String,
  conditions: List[Condition] = List.empty[Condition],
  validators: List[Validator] = List.empty[Validator]
) {

  def validate(): ValidatorResult = ValidatorResult(isValid(), message())

  private def isValid(): Boolean = {
    conditions.forall(_.validationFunction()) && validators.forall(_.isValid())
  }

  private def message(): String = message(0).trim

  def +(validator: Validator): Validator = {
    Validator(source, this.conditions ::: validator.conditions, this.validators ::: validator.validators)
  }

  private def message(indentation: Int): String = {
    val messageBuilder = new StringBuilder(enrichMessage(source, indentation))
    val newIndentation = indentation + 1
    val conditionsMessage = conditions.filter(!_.validationFunction()).map(_.message).map(enrichMessage(_, newIndentation)).mkString
    messageBuilder.append(conditionsMessage)
    validators.foreach(validator => messageBuilder.append(validator.message(newIndentation)))
    messageBuilder.toString()
  }

  private def enrichMessage(message: String, indent: Int) = "\t" * indent + message + "\n"
}
