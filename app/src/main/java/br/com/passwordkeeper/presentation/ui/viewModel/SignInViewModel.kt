package br.com.passwordkeeper.presentation.ui.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.usecase.FormValidationSignInUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.usecase.AdviceUseCase
import br.com.passwordkeeper.domain.usecase.FormValidationSignInUseCase
import br.com.passwordkeeper.domain.usecase.SignInUseCase
import br.com.passwordkeeper.extensions.isValidEmail
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val formValidationSignInUseCase: FormValidationSignInUseCase
) : ViewModel() {

    private val _signIn = MutableLiveData<SignInUseCaseResult>()
    val signIn: LiveData<SignInUseCaseResult>
        get() = _signIn

    private val _formValidation = MutableLiveData<FormValidationSignInUseCaseResult>()
    val formValidation: LiveData<FormValidationSignInUseCaseResult>
        get() = _formValidation

    fun updateSignIn(email: String, password: String) {
        //primeiro implementar o formValidationSignInUseCase
        val formValidationSignInUseCaseResult: FormValidationSignInUseCaseResult


        viewModelScope.launch {
            when (val signInUseCaseResult = signInUseCase.signIn(email = "francis@teste.com.br", password = "Teste123")) {
                is SignInUseCaseResult.Success -> {
                    _signIn.postValue(SignInUseCaseResult.Success(signInUseCaseResult.userView))
                }
                is SignInUseCaseResult.ErrorEmailOrPasswordWrong -> {
                    _signIn.postValue(SignInUseCaseResult.ErrorEmailOrPasswordWrong)
                }
                is SignInUseCaseResult.ErrorUnknown -> {
                    _signIn.postValue(SignInUseCaseResult.ErrorUnknown)
                }
            }
        }

    }

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    fun updateEmail(email: String) {
        _email.postValue(email)
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

   fun updatePassword(password: String) {
       _password.postValue(password)
   }

}

