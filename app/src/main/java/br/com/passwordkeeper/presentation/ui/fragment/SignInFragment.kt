package br.com.passwordkeeper.presentation.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignInStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.SignInStateResult
import br.com.passwordkeeper.extensions.showMessage
import br.com.passwordkeeper.presentation.ui.viewmodel.SignInViewModel
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class SignInFragment : Fragment(R.layout.login_fragment) {
    private val navController by lazy {
        findNavController()
    }
    private lateinit var binding: LoginFragmentBinding
    private val signInViewModel: SignInViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
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

    private fun textInputLayoutWithoutError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
    }

    private fun textInputLayoutWithError(textInputLayout: TextInputLayout) {
        textInputLayout.boxStrokeColor = ContextCompat
            .getColor(requireActivity(), R.color.red)
        val colorInt = ContextCompat.getColor(requireActivity(), R.color.red)
        val csl = ColorStateList.valueOf(colorInt)
        textInputLayout.hintTextColor = csl
    }

    private fun observeFormValidation() {
        signInViewModel.formValidationState.observe(viewLifecycleOwner) { formValidationSignInStateResult ->
            when (formValidationSignInStateResult) {
                is FormValidationSignInStateResult.ErrorEmailIsBlank -> {
                    binding.tiEmail.error = context?.getString(R.string.email_field_is_empty)
                    textInputLayoutWithError(binding.tiEmail)
                    textInputLayoutWithoutError(binding.tiPassword)
                }
                is FormValidationSignInStateResult.ErrorEmailMalFormed -> {
                    binding.tiEmail.error = context?.getString(R.string.invalid_email)
                    textInputLayoutWithError(binding.tiEmail)
                    textInputLayoutWithoutError(binding.tiPassword)
                }
                is FormValidationSignInStateResult.ErrorPasswordIsBlank -> {
                    binding.tiPassword.error = context?.getString(R.string.password_field_is_empty)
                    textInputLayoutWithError(binding.tiPassword)
                    textInputLayoutWithoutError(binding.tiEmail)
                }
                is FormValidationSignInStateResult.Success -> {
                    val email = formValidationSignInStateResult.email
                    textInputLayoutWithoutError(binding.tiEmail)
                    val password = formValidationSignInStateResult.password
                    textInputLayoutWithoutError(binding.tiPassword)
                    signInViewModel.updateSignInState(email, password)
                }
                is FormValidationSignInStateResult.EmptyState -> {}
            }
        }
    }

    private fun observeSignIn() {
        signInViewModel.signInState.observe(viewLifecycleOwner) { signInStateResult ->
            when (signInStateResult) {
                is SignInStateResult.Success -> {
                    val userView = signInStateResult.userView
                    val directions =
                        SignInFragmentDirections.actionLoginFragmentToHomeFragment(userView)
                    navController.navigate(directions)
                    signInViewModel.updateStatesToEmptyState()
                }
                is SignInStateResult.ErrorEmailOrPasswordWrong -> {
                    view?.showMessage(getString(R.string.email_or_password_wrong))
                }
                is SignInStateResult.ErrorUnknown -> {
                    view?.showMessage(getString(R.string.error))
                }
                is SignInStateResult.EmptyState -> {

                }
            }
        }
    }
}
