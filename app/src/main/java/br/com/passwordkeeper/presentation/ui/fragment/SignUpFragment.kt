package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.SignUpFragmentBinding
import br.com.passwordkeeper.domain.usecase.SignInUseCase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpFragmentBinding
    private val loginUseCase: SignInUseCase by inject()

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
        binding.buttonSignUpCreateAccount.setOnClickListener {
            Log.i("testando", "testando click no botão")
            val email = binding.inputSignUpEmail.text.toString()
            if (email.isNotBlank()) {
                lifecycleScope.launch {
                    loginUseCase.createUser(
                        email = email,
                        password = "Teste123"
                    )
                }
            }
        }
    }

    fun setupBackButton() {
        val buttonBack: ImageButton = binding.buttonBack
        buttonBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    fun setupCreateAccountButton() {
        val createAccount: MaterialButton = binding.buttonSignUpCreateAccount
        createAccount.setOnClickListener {
            val directions =
                SignUpFragmentDirections.actionSignUpFragmentToSignUpCongratsFragment2()
            navController.navigate(directions)
        }
    }
}