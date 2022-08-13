package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.SignUpCongratsFragmentBinding
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SignUpCongratsFragment : Fragment(R.layout.sign_up_congrats_fragment) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: SignUpCongratsFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpCongratsFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setupProceedButton()
    }

    private fun setupProceedButton() {
        binding.materialButtonProceed.setOnClickListener {
            navController.popBackStack()
        }
    }
}