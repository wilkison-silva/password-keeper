package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.LoginFragmentBinding
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {
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