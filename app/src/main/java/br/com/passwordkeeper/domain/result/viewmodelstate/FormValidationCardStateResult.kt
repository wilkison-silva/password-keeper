package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class FormValidationCardStateResult {
    object Success : FormValidationCardStateResult()
    object DescriptionIsEmpty: FormValidationCardStateResult()
    object CategoryNotSelected: FormValidationCardStateResult()
}