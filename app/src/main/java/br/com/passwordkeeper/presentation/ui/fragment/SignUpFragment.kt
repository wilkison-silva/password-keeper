package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.SignUpFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateUserStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignUpStateResult
import br.com.passwordkeeper.extensions.showMessage
import br.com.passwordkeeper.extensions.textInputEditText
import br.com.passwordkeeper.presentation.ui.viewModel.SignUpViewModel
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpFragmentBinding
    private val signUpViewModel: SignUpViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignUpFragmentBinding
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
        setupBackButton()
        setupCreateAccountButton()
        observeFormValidation()
        observeSignUp()
    }

    private fun setupBackButton() {
        val buttonBack: ImageButton = binding.buttonBack
        buttonBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observeFormValidation() {
        signUpViewModel.formValidationState.observe(viewLifecycleOwner) { formValidationSignUpStateResult ->
            when (formValidationSignUpStateResult) {
                is FormValidationSignUpStateResult.ErrorEmailIsBlank ->
                    view?.let {
                        showMessage(it, getString(R.string.email_field_is_empty))
                    }
                is FormValidationSignUpStateResult.ErrorEmailMalFormed ->
                    view?.let {
                        showMessage(it, getString(R.string.invalid_email))
                    }
                is FormValidationSignUpStateResult.ErrorNameIsBlank ->
                    view?.let {
                        showMessage(it, getString(R.string.name_field_is_empty))
                    }
                is FormValidationSignUpStateResult.ErrorPasswordIsBlank ->
                    view?.let {
                        showMessage(it, getString(R.string.password_field_is_empty))
                    }
                is FormValidationSignUpStateResult.ErrorPasswordTooWeak ->
                    view?.let {
                        showMessage(it, getString(R.string.password_weak))
                    }
                is FormValidationSignUpStateResult.ErrorPasswordsDoNotMatch ->
                    view?.let {
                        showMessage(it, getString(R.string.password_not_match))
                    }
                is FormValidationSignUpStateResult.Success -> {
                    val name = formValidationSignUpStateResult.name
                    val email = formValidationSignUpStateResult.email
                    val password = formValidationSignUpStateResult.password
                    signUpViewModel.updateSignUpState(name, email, password)
                }
                is FormValidationSignUpStateResult.EmptyState -> {

                }

            }
        }
    }

    private fun observeSignUp() {
        signUpViewModel.createUserState.observe(viewLifecycleOwner) { createUserStateResult ->
            when (createUserStateResult) {
                is CreateUserStateResult.ErrorEmailAlreadyExists ->
                    view?.let {
                        showMessage(it, getString(R.string.email_already_exist))
                    }
                is CreateUserStateResult.ErrorEmailMalformed ->
                    view?.let {
                        showMessage(it, getString(R.string.invalid_email))
                    }
                is CreateUserStateResult.ErrorUnknown ->
                    view?.let {
                        showMessage(it, getString(R.string.error))
                    }
                is CreateUserStateResult.ErrorWeakPassword ->
                    view?.let {
                        showMessage(it, getString(R.string.password_weak))
                    }
                is CreateUserStateResult.Success -> {
                    val directions =
                        SignUpFragmentDirections.actionSignUpFragmentToSignUpCongratsFragment2()
                    navController.navigate(directions)
                    signUpViewModel.updateStatesToEmptyState()
                }
                is CreateUserStateResult.EmptyState -> {

                }
            }
        }
    }

    private fun setupCreateAccountButton() {
        binding.buttonSignUpCreateAccount.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputSignInEmail.text.toString()
            val password = binding.inputSignUpPassword.text.toString()
            val confirmedPassword = binding.inputConfirmPassword.text.toString()
            signUpViewModel.updateFormValidationState(name, email, password, confirmedPassword)
            it.textInputEditText()
        }
    }
}
