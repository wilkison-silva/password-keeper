package br.com.passwordkeeper.domain.result.usecase

sealed class ErrorsValidationPassword {
    object ErrorOneUpperLetterNotFound: ErrorsValidationPassword()
    object ErrorOneLowerLetterNotFound: ErrorsValidationPassword()
    object ErrorOneSpecialCharacterNotFound: ErrorsValidationPassword()
    object ErrorOneNumericCharacterNotFound: ErrorsValidationPassword()
    object ErrorPasswordLengthNotMatch: ErrorsValidationPassword()
}