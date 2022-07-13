package br.com.passwordkeeper.presenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.LoginFragmentBinding

class LoginFragment: Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: LoginFragmentBinding
    private lateinit var layoutSingnUpFragmentBinding: LoginFragmentBinding

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
        val email = binding.inputEmail.text.toString()
//        val google = binding.mbGoogle.setOnClickListener(layoutSingnUpFragmentBinding.mbGoogle)
    }
}