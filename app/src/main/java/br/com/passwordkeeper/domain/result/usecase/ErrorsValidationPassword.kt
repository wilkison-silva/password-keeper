package br.com.passwordkeeper.domain.result.usecase

sealed class ErrorsValidationPassword {
    object ErrorOneUpperLetterNotFound: ErrorsValidationPassword()
    object ErrorOneLowerLetterNotFound: ErrorsValidationPassword()
    object ErrorOneSpecialLetterNotFound: ErrorsValidationPassword()
    object ErrorOneNumericLetterNotFound: ErrorsValidationPassword()
    object ErrorPasswordSizeNotMatch: ErrorsValidationPassword()
}