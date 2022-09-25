package br.com.passwordkeeper.presentation.features.sign_up

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
import br.com.passwordkeeper.databinding.FragmentSignUpBinding

import br.com.passwordkeeper.extensions.hideKeyboard
import br.com.passwordkeeper.extensions.showSnackBar
import br.com.passwordkeeper.extensions.withError
import br.com.passwordkeeper.extensions.withoutError
import br.com.passwordkeeper.presentation.features.MainViewModel
import br.com.passwordkeeper.presentation.features.sign_up.states.CreateUserState
import br.com.passwordkeeper.presentation.features.sign_up.states.FormValidationSignUpState
import br.com.passwordkeeper.presentation.features.sign_up.states.ValidationState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
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
                is FormValidationSignUpState.ErrorEmailIsBlank -> {
                    binding.textInputSignUpEmail.error = context?.getString(R.string.email_is_blank)
                    binding.textInputSignUpEmail.withError()
                    binding.textInputName.withoutError()
                    binding.textInputSignUpPassword.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()
                }
                is FormValidationSignUpState.ErrorEmailMalFormed -> {
                    binding.textInputSignUpEmail.error = context?.getString(R.string.invalid_email)
                    binding.textInputSignUpEmail.withError()
                    binding.textInputName.withoutError()
                    binding.textInputSignUpPassword.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()
                }

                is FormValidationSignUpState.ErrorNameIsBlank -> {
                    binding.textInputName.error = context?.getString(R.string.name_field_is_empty)
                    binding.textInputName.withError()
                    binding.textInputSignUpEmail.withoutError()
                    binding.textInputSignUpPassword.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()
                }

                is FormValidationSignUpState.ErrorPasswordIsBlank -> {
                    binding.textInputSignUpPassword.error = context?.getString(R.string.password_field_is_empty)
                    binding.textInputSignUpPassword.withError()
                    binding.textInputName.withoutError()
                    binding.textInputSignUpEmail.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()

                }

                is FormValidationSignUpState.ErrorPasswordTooWeak -> {
                    binding.textInputSignUpPassword.error = context?.getString(R.string.password_weak)
                    binding.textInputSignUpPassword.withError()
                    binding.textInputName.withoutError()
                    binding.textInputSignUpEmail.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()
                }

                is FormValidationSignUpState.ErrorPasswordsDoNotMatch -> {
                    binding.textInputSignUpConfirmPassword.error = context?.getString(R.string.password_not_match)
                    binding.textInputSignUpConfirmPassword.withError()
                    binding.textInputName.withoutError()
                    binding.textInputSignUpEmail.withoutError()
                    binding.textInputSignUpPassword.withoutError()
                }

                is FormValidationSignUpState.Success -> {
                    val name = formValidationSignUpStateResult.name
                    binding.textInputName.withoutError()
                    val email = formValidationSignUpStateResult.email
                    binding.textInputSignUpEmail.withoutError()
                    val password = formValidationSignUpStateResult.password
                    binding.textInputSignUpPassword.withoutError()
                    binding.textInputSignUpConfirmPassword.withoutError()
                    signUpViewModel.updateSignUpState(name, email, password)
                }
                is FormValidationSignUpState.EmptyState -> {}
            }
        }
    }

    private fun observeSignUp() {
        signUpViewModel.createUserState.observe(viewLifecycleOwner) { createUserStateResult ->
            when (createUserStateResult) {
                is CreateUserState.ErrorEmailAlreadyExists ->
                    view?.showSnackBar(getString(R.string.email_already_exist))
                is CreateUserState.ErrorEmailMalformed ->
                    view?.showSnackBar(getString(R.string.invalid_email))
                is CreateUserState.ErrorUnknown ->
                    view?.showSnackBar(getString(R.string.error))
                is CreateUserState.ErrorWeakPassword ->
                    view?.showSnackBar(getString(R.string.password_weak))
                is CreateUserState.Success -> {
                    val directions =
                        SignUpFragmentDirections.actionFragmentSignUpToFragmentSignUpCongrats()
                    navController.navigate(directions)
                    signUpViewModel.updateStatesToEmptyState()
                }
                is CreateUserState.EmptyState -> {}
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
                is ValidationState.Error -> {
                    binding.textViewUpperCase.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationState.Success -> {
                    binding.textViewUpperCase.setTextColor(getColorStateList(R.color.green))
                }
            }
        }
        signUpViewModel.passwordLowerLetterState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationState.Error -> {
                    binding.textViewLowerCase.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationState.Success -> {
                    binding.textViewLowerCase.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordSpecialCharacterState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationState.Error -> {
                    binding.textViewSpecialCharacter.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationState.Success -> {
                    binding.textViewSpecialCharacter.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordNumericCharactersState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationState.Error -> {
                    binding.textViewNumericCharacter.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationState.Success -> {
                    binding.textViewNumericCharacter.setTextColor(getColorStateList(R.color.green))
                }
            }
        }

        signUpViewModel.passwordLengthState.observe(viewLifecycleOwner) { validationStateResult ->
            when (validationStateResult) {
                is ValidationState.Error -> {
                    binding.textViewPasswordLength.setTextColor(getColorStateList(R.color.red))
                }
                is ValidationState.Success -> {
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