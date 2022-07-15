package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import br.com.passwordkeeper.domain.usecase.LoginUseCase
import br.com.passwordkeeper.domain.usecase.LoginUseCaseImpl
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private val loginUseCase: LoginUseCase by inject()

    private lateinit var binding: LoginFragmentBinding

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
        binding.tvTitle.setOnClickListener {
            lifecycleScope.launch {
                loginUseCase.getCurrentUser()
            }
        }
        binding.mbGoogle.setOnClickListener {
            lifecycleScope.launch {
                loginUseCase.singOut()
            }
        }
    }

    private fun setupSignInButton() {
        binding.mbSignIn.setOnClickListener {
            lifecycleScope.launch {
                loginUseCase.signIn(
                    email = "teste@teste.com.br",
                    password = "Teste123"
                )
            }
        }
    }

    fun setupSignUpButton() {
        val mbSignUp: MaterialButton = binding.mbSignUp
        mbSignUp.setOnClickListener{
            val directions =
                LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            navController.navigate(directions)
        }
    }
}