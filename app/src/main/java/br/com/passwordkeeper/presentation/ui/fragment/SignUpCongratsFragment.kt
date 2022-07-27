package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.SignUpCongratsFragmentBinding

class SignUpCongratsFragment : Fragment() {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpCongratsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignUpCongratsFragmentBinding
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
        setupProceedButton()
    }

    private fun setupProceedButton() {
        binding.materialButtonProceed.setOnClickListener {
            navController.popBackStack()
        }
    }
}