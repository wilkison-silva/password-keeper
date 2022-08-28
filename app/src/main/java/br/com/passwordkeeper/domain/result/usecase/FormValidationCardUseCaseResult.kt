package br.com.passwordkeeper.domain.result.usecase

sealed class FormValidationCardUseCaseResult {
    object Success : FormValidationCardUseCaseResult()
    object DescriptionIsEmpty: FormValidationCardUseCaseResult()
    object CategoryNotSelected: FormValidationCardUseCaseResult()
}