package br.com.passwordkeeper.domain.result.usecase

sealed class FormValidationSignUpUseCaseResult {
    object Success : FormValidationSignUpUseCaseResult()
    object ErrorNameIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailMalFormed: FormValidationSignUpUseCaseResult()
    object ErrorPasswordIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorOneUpperLetterNotFound: FormValidationSignUpUseCaseResult()
    object ErrorOneLowerLetterNotFound: FormValidationSignUpUseCaseResult()
    object ErrorOneSpecialCharacterNotFound: FormValidationSignUpUseCaseResult()
    object ErrorOneNumericCharacterNotFound: FormValidationSignUpUseCaseResult()
    object ErrorPasswordLengthNotMatch: FormValidationSignUpUseCaseResult()
    object ErrorPasswordsDoNotMatch: FormValidationSignUpUseCaseResult()
}