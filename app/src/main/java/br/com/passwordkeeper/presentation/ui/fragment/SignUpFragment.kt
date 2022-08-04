package br.com.passwordkeeper.presentation.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.SignUpFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateUserStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignUpStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.ValidationStateResult
import br.com.passwordkeeper.extensions.hideKeyboard
import br.com.passwordkeeper.extensions.showMessage
import br.com.passwordkeeper.presentation.ui.viewmodel.SignUpViewModel
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment(R.layout.sign_up_fragment) {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpFragmentBinding
    private val signUpViewModel: SignUpViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
        setupBackButton()
        setupCreateAccountButton()
        setupConfirmedPasswordEditText()
        observeFormValidation()
        observeSignUp()
        setupEditText()
        observePasswordValidation()
        observeValidationStates()
    }

    private fun setupConfirmedPasswordEditText() {
        binding.inputConfirmPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.inputConfirmPassword.hideKeyboard()
            }
            true
        }
    }

    private fun setupBackButton() {
        val buttonBack: ImageButton = binding.imageButtonBack
        buttonBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observeFormValidation() {
        signUpViewModel.formValidationState.observe(viewLifecycleOwner) { formValidationSignUpStateResult ->
            when (formValidationSignUpStateResult) {
                is FormValidationSignUpStateResult.ErrorEmailIsBlank ->
                    view?.showMessage(getString(R.string.email_field_is_empty))
                is FormValidationSignUpStateResult.ErrorEmailMalFormed ->
                    view?.showMessage(getString(R.string.invalid_email))
                is FormValidationSignUpStateResult.ErrorNameIsBlank ->
                    view?.showMessage(getString(R.string.name_field_is_empty))
                is FormValidationSignUpStateResult.ErrorPasswordIsBlank ->
                    view?.showMessage(getString(R.string.password_field_is_empty))
                is FormValidationSignUpStateResult.ErrorPasswordTooWeak ->
                    view?.showMessage(getString(R.string.password_weak))
                is FormValidationSignUpStateResult.ErrorPasswordsDoNotMatch ->
                    view?.showMessage(getString(R.string.password_not_match))
                is FormValidationSignUpStateResult.Success -> {
                    val name = formValidationSignUpStateResult.name
                    val email = formValidationSignUpStateResult.email
                    val password = formValidationSignUpStateResult.password
                    signUpViewModel.updateSignUpState(name, email, password)
                }
                is FormValidationSignUpStateResult.EmptyState -> {}
            }
        }
    }

    private fun observeSignUp() {
        signUpViewModel.createUserState.observe(viewLifecycleOwner) { createUserStateResult ->
            when (createUserStateResult) {
                is CreateUserStateResult.ErrorEmailAlreadyExists ->
                    view?.showMessage(getString(R.string.email_already_exist))
                is CreateUserStateResult.ErrorEmailMalformed ->
                    view?.showMessage(getString(R.string.invalid_email))
                is CreateUserStateResult.ErrorUnknown ->
                    view?.showMessage(getString(R.string.error))
                is CreateUserStateResult.ErrorWeakPassword ->
                    view?.showMessage(getString(R.string.password_weak))

                is CreateUserStateResult.Success -> {
                    val directions =
                        SignUpFragmentDirections.actionSignUpFragmentToSignUpCongratsFragment2()
                    navController.navigate(directions)
                    signUpViewModel.updateStatesToEmptyState()
                }
                is CreateUserStateResult.EmptyState -> {}
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
        }
    }

    private fun getColorStateList(@ColorRes color: Int): ColorStateList {
        val colorInt = ContextCompat.getColor(requireActivity(), color)
        return ColorStateList.valueOf(colorInt)
    }

    private fun observeValidationStates() {
        signUpViewModel.passwordUpperLetterState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationStateResult.Error -> {
                    binding.textViewUpperCase.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationStateResult.Success -> {
                    binding.textViewUpperCase.setTextColor(getColorStateList(R.color.green))
                }
            }
        }
        signUpViewModel.passwordLowerLetterState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationStateResult.Error -> {
                    binding.textViewLowerCase.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationStateResult.Success -> {
                    binding.textViewLowerCase.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordSpecialCharacterState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationStateResult.Error -> {
                    binding.textViewSpecialCharacter.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationStateResult.Success -> {
                    binding.textViewSpecialCharacter.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordNumericCharactersState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationStateResult.Error -> {
                    binding.textViewNumericCharacter.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationStateResult.Success -> {
                    binding.textViewNumericCharacter.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordLengthState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationStateResult.Error -> {
                    binding.textViewPasswordLength.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationStateResult.Success -> {
                    binding.textViewPasswordLength.setTextColor(getColorStateList(R.color.green))
                }
            }
        }
    }

    private fun observePasswordValidation() {
        signUpViewModel.passwordFieldIsEmptyState.observe(viewLifecycleOwner) {
            binding.textViewUpperCase.setTextColor(getColorStateList(R.color.gray_dark))
            binding.textViewLowerCase.setTextColor(getColorStateList(R.color.gray_dark))
            binding.textViewSpecialCharacter.setTextColor(getColorStateList(R.color.gray_dark))
            binding.textViewNumericCharacter.setTextColor(getColorStateList(R.color.gray_dark))
            binding.textViewPasswordLength.setTextColor(getColorStateList(R.color.gray_dark))
        }
    }

    private fun setupEditText() {
        binding.inputSignUpPassword.addTextChangedListener {
            val password = binding.inputSignUpPassword.text.toString()
            signUpViewModel.updatePasswordValidation(password)
        }
    }
}
