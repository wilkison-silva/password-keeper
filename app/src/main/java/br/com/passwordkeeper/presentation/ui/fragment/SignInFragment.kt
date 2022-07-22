package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import com.google.android.material.button.MaterialButton

class SignInFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

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
        mbSignIn.setOnClickListener{
            val directions =
              SignInFragmentDirections.actionLoginFragmentToHomeFragment()
            navController.navigate(directions)
        }
    }
}