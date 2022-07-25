package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignInStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.SignInStateResult
import br.com.passwordkeeper.extensions.showMessage
import br.com.passwordkeeper.presentation.ui.viewModel.SignInViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject

class SignInFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var emailTextInputEditText: TextInputEditText
    private lateinit var binding: LoginFragmentBinding
    private val signInViewModel: SignInViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginFragmentBinding
            .inflate(
                inflater,
                container,
                false
            )
        this.binding = binding
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignUpButton()
        setupSignInButton()
        observeSignIn()
        observeFormValidation()
    }

    private fun setupSignUpButton() {
        val mbSignUp: MaterialButton = binding.mbSignUp
        mbSignUp.setOnClickListener {
            val directions =
                SignInFragmentDirections.actionLoginFragmentToSignUpFragment()
            navController.navigate(directions)
        }
    }

    private fun setupSignInButton() {
        val mbSignIn: MaterialButton = binding.mbSignIn
        mbSignIn.setOnClickListener {
            signInViewModel.updateFormValidationState(email = "francis@teste.com.br", password = "Teste123")
        }

    }

    private fun observeFormValidation() {
        signInViewModel.formValidationState.observe(viewLifecycleOwner) {
            when (it) {
               is FormValidationSignInStateResult.ErrorEmailIsBlank ->
                   view?.let {
                       showMessage(it, "Email is blank")
                   }
               is FormValidationSignInStateResult.ErrorEmailMalFormed ->
                   view?.let {
                       showMessage(it, "Email not accepted")
                   }
               is FormValidationSignInStateResult.ErrorPasswordIsBlank ->
                   view?.let {
                       showMessage(it, "Password is blank")
                   }
               is FormValidationSignInStateResult.Success -> {
                   val email = it.email
                   val password = it.password
                   signInViewModel.updateSignInState(email, password)
               }

            }
        }
    }

    private fun observeSignIn() {
        signInViewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                is SignInStateResult.Success -> {
                    val userView = it.userView
                    view?.let {
                        showMessage(it, "Login com sucesso")
                    }
                }
                is SignInStateResult.ErrorEmailOrPasswordWrong -> {
                    view?.let {
                        showMessage(it, getString(R.string.email_or_password_wrong))
                    }
                }
                is SignInStateResult.ErrorUnknown -> {
                    view?.let {
                        showMessage(it, getString(R.string.error))
                    }
                }
            }
        }
    }
}