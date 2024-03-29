package br.com.passwordkeeper.presentation.features.sign_in

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentSignInBinding
import br.com.passwordkeeper.extensions.hideKeyboard
import br.com.passwordkeeper.extensions.showSnackBar
import br.com.passwordkeeper.extensions.withError
import br.com.passwordkeeper.extensions.withoutError
import br.com.passwordkeeper.presentation.features.MainViewModel
import br.com.passwordkeeper.presentation.features.sign_in.states.FormValidationSignInState
import br.com.passwordkeeper.presentation.features.sign_in.states.SignInState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val navController by lazy {
        findNavController()
    }
    private lateinit var binding: FragmentSignInBinding
    private val signInViewModel: SignInViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
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
                SignInFragmentDirections.actionFragmentSignInToFragmentSignUp()
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
        lifecycleScope.launchWhenStarted {
            signInViewModel.formValidationState.collect { formValidationSignInStateResult ->
                when (formValidationSignInStateResult) {
                    is FormValidationSignInState.ErrorEmailIsBlank -> {
                        binding.tiEmail.error = context?.getString(R.string.email_field_is_empty)
                        binding.tiEmail.withError()
                        binding.tiPassword.withoutError()
                    }
                    is FormValidationSignInState.ErrorEmailMalFormed -> {
                        binding.tiEmail.error = context?.getString(R.string.invalid_email)
                        binding.tiEmail.withError()
                        binding.tiPassword.withoutError()
                    }
                    is FormValidationSignInState.ErrorPasswordIsBlank -> {
                        binding.tiPassword.error = context?.getString(R.string.password_field_is_empty)
                        binding.tiPassword.withError()
                        binding.tiEmail.withoutError()

                    }
                    is FormValidationSignInState.Success -> {
                        val email = formValidationSignInStateResult.email
                        binding.tiEmail.withoutError()
                        val password = formValidationSignInStateResult.password
                        binding.tiPassword.withoutError()
                        signInViewModel.updateSignInState(email, password)

                    }
                    is FormValidationSignInState.EmptyState -> {}
                }
            }
        }
    }

    private fun observeSignIn() {
        lifecycleScope.launchWhenStarted {
            signInViewModel.signInState.collect { signInStateResult ->
                when (signInStateResult) {
                    is SignInState.Success -> {
                        val directions =
                            SignInFragmentDirections.actionFragmentSignInToFragmentHome()
                        navController.navigate(directions)
                        signInViewModel.updateStatesToEmptyState()
                    }
                    is SignInState.ErrorEmailOrPasswordWrong -> {
                        view?.showSnackBar(getString(R.string.email_or_password_wrong))
                        binding.mbSignIn.text = getString(R.string.sign_in)
                        binding.progressBarSignIn.visibility = GONE
                    }
                    is SignInState.ErrorUnknown -> {
                        view?.showSnackBar(getString(R.string.error))
                        binding.mbSignIn.text = getString(R.string.sign_in)
                        binding.progressBarSignIn.visibility = GONE
                    }
                    is SignInState.EmptyState -> {

                    }
                    is SignInState.Loading -> {
                        binding.mbSignIn.text = getString(R.string.please_wait)
                        binding.progressBarSignIn.visibility = VISIBLE
                    }
                }
            }
        }
    }
}
