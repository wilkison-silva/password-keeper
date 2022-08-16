package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignInStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.SignInStateResult
import br.com.passwordkeeper.domain.usecase.CardUseCase
import br.com.passwordkeeper.extensions.hideKeyboard
import br.com.passwordkeeper.extensions.showSnackBar
import br.com.passwordkeeper.extensions.withError
import br.com.passwordkeeper.extensions.withoutError
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.login_fragment) {
    private val navController by lazy {
        findNavController()
    }
    private lateinit var binding: LoginFragmentBinding
    private val signInViewModel: SignInViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setupSignUpButton()
        setupSignInButton()
        setupPasswordEditText()
        observeSignIn()
        observeFormValidation()
    }

    private fun setupPasswordEditText() {
        binding.inputPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.inputPassword.hideKeyboard()
            }
            true
        }
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
        signInViewModel.formValidationState.observe(viewLifecycleOwner) { formValidationSignInStateResult ->
            when (formValidationSignInStateResult) {
                is FormValidationSignInStateResult.ErrorEmailIsBlank -> {
                    binding.tiEmail.error = context?.getString(R.string.email_field_is_empty)
                    binding.tiEmail.withError()
                    binding.tiPassword.withoutError()
                }
                is FormValidationSignInStateResult.ErrorEmailMalFormed -> {
                    binding.tiEmail.error = context?.getString(R.string.invalid_email)
                    binding.tiEmail.withError()
                    binding.tiPassword.withoutError()
                }
                is FormValidationSignInStateResult.ErrorPasswordIsBlank -> {
                    binding.tiPassword.error = context?.getString(R.string.password_field_is_empty)
                    binding.tiPassword.withError()
                    binding.tiEmail.withoutError()

                }
                is FormValidationSignInStateResult.Success -> {
                    val email = formValidationSignInStateResult.email
                    binding.tiEmail.withoutError()
                    val password = formValidationSignInStateResult.password
                    binding.tiPassword.withoutError()
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
                    val directions =
                        SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                    navController.navigate(directions)
                    signInViewModel.updateStatesToEmptyState()
                }
                is SignInStateResult.ErrorEmailOrPasswordWrong -> {
                    view?.showSnackBar(getString(R.string.email_or_password_wrong))
                    binding.mbSignIn.text = getString(R.string.sign_in)
                    binding.progressBarSignIn.visibility = GONE
                }
                is SignInStateResult.ErrorUnknown -> {
                    view?.showSnackBar(getString(R.string.error))
                    binding.mbSignIn.text = getString(R.string.sign_in)
                    binding.progressBarSignIn.visibility = GONE
                }
                is SignInStateResult.EmptyState -> {

                }
                is SignInStateResult.Loading -> {
                    binding.mbSignIn.text = getString(R.string.please_wait)
                    binding.progressBarSignIn.visibility = VISIBLE
                }
            }
        }
    }
}
