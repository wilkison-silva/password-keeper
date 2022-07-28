package br.com.passwordkeeper.domain.result.usecase

sealed class PasswordValidationUseCaseResult {
    object Success : PasswordValidationUseCaseResult()
    object ErrorOneUpperLetterNotFound: PasswordValidationUseCaseResult()
    object ErrorOneLowerLetterNotFound: PasswordValidationUseCaseResult()
    object ErrorOneSpecialCharacterNotFound: PasswordValidationUseCaseResult()
    object ErrorOneNumericCharacterNotFound: PasswordValidationUseCaseResult()
    object ErrorPasswordLengthNotMatch: PasswordValidationUseCaseResult()
}