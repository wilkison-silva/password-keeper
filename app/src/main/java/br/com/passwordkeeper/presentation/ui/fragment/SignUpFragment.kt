package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.SignUpFragmentBinding
import com.google.android.material.button.MaterialButton

class SignUpFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpFragmentBinding

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
    }

    private fun setupBackButton() {
        val buttonBack: ImageButton = binding.buttonBack
        buttonBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun setupCreateAccountButton() {
        val createAccount: MaterialButton = binding.buttonSignUpCreateAccount
        createAccount.setOnClickListener {
            val directions =
                SignUpFragmentDirections.actionSignUpFragmentToSignUpCongratsFragment2()
            navController.navigate(directions)
        }
    }
}