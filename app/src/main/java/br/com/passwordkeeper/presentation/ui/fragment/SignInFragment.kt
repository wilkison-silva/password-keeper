package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.presentation.ui.viewModel.HomeViewModel
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
    }

    private fun setupSignUpButton() {
        val mbSignUp: MaterialButton = binding.mbSignUp
        mbSignUp.setOnClickListener{
            val directions =
                SignInFragmentDirections.actionLoginFragmentToSignUpFragment()
            navController.navigate(directions)
        }
    }

    private fun setupSignInButton() {
        val mbSignIn: MaterialButton = binding.mbSignIn
        mbSignIn.setOnClickListener {
            signInViewModel.updateSignIn()
        }
//            val directions =
//                SignInFragmentDirections.
//            navController.navigate(directions)

        }


    private fun observeSignIn() {
        signInViewModel.signIn.observe(viewLifecycleOwner) {
            when (it) {
                is SignInUseCaseResult.Success -> {
                    val userView = it.userView
                    binding.mbSignIn.text = userView.name
                    binding.mbSignIn.text = userView.firstCharacterName
                }
                is SignInUseCaseResult.ErrorEmailOrPasswordWrong -> {
                    binding.mbSignIn.text = getString(R.string.email_or_password_wrong)
                }
                is SignInUseCaseResult.ErrorUnknown -> {
                    binding.mbSignIn.text = getString(R.string.error)

                }
            }
        }
    }
}