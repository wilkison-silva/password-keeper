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
import org.koin.android.ext.android.inject

class SignInFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }
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
        binding.materialButtonSignUp.setOnClickListener {
            val directions =
                SignInFragmentDirections.actionLoginFragmentToSignUpFragment()
            navController.navigate(directions)
        }
    }

    private fun setupSignInButton() {
        binding.mbSignIn.setOnClickListener {
            val email = binding.inputSignInEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            signInViewModel.updateFormValidationState(email, password)
        }
    }

    private fun observeFormValidation() {
        signInViewModel.formValidationState.observe(viewLifecycleOwner) {formValidationSignInStateResult ->
            when (formValidationSignInStateResult) {
                is FormValidationSignInStateResult.ErrorEmailIsBlank ->
                    view?.let {
                        showMessage(it, getString(R.string.email_field_is_empty))
                    }

                is FormValidationSignInStateResult.ErrorEmailMalFormed ->
                    view?.let {
                        showMessage(it, getString(R.string.invalid_email))
                    }
                is FormValidationSignInStateResult.ErrorPasswordIsBlank ->
                    view?.let {
                        showMessage(it, getString(R.string.password_field_is_empty))
                    }
                is FormValidationSignInStateResult.Success -> {
                    val email = formValidationSignInStateResult.email
                    val password = formValidationSignInStateResult.password
                    signInViewModel.updateSignInState(email, password)
                }
                is FormValidationSignInStateResult.EmptyState -> {

                }
            }
        }
    }

    private fun observeSignIn() {
        signInViewModel.signInState.observe(viewLifecycleOwner) {signInStateResult ->
            when (signInStateResult) {
                is SignInStateResult.Success -> {
                    val userView = signInStateResult.userView
                    val directions =
                        SignInFragmentDirections.actionLoginFragmentToHomeFragment(userView)
                    navController.navigate(directions)
                    signInViewModel.updateStatesToEmptyState()
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
                is SignInStateResult.EmptyState -> {

                }
            }
        }
    }
}
