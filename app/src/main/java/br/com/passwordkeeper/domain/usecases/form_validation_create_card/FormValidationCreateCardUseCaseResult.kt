package br.com.passwordkeeper.domain.usecases.form_validation_create_card

sealed class FormValidationCreateCardUseCaseResult {
    object Success : FormValidationCreateCardUseCaseResult()
    object DescriptionIsEmpty: FormValidationCreateCardUseCaseResult()
    object CategoryNotSelected: FormValidationCreateCardUseCaseResult()
}