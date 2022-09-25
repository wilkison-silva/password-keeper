package br.com.passwordkeeper.presentation.features.sign_up_congrats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentSignUpCongratsBinding

import br.com.passwordkeeper.presentation.features.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpCongratsFragment : Fragment(R.layout.fragment_sign_up_congrats) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentSignUpCongratsBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpCongratsBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setupProceedButton()
    }

    private fun setupProceedButton() {
        binding.materialButtonProceed.setOnClickListener {
            navController.popBackStack()
        }
    }
}