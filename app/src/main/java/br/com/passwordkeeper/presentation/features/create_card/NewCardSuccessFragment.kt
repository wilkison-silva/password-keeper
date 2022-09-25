package br.com.passwordkeeper.presentation.features.create_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentNewCardSuccessBinding
import br.com.passwordkeeper.presentation.features.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewCardSuccessFragment : Fragment(R.layout.fragment_new_card_success) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentNewCardSuccessBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewCardSuccessBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        lifecycleScope.launch {
            delay(2500)
            navController.popBackStack()
        }
    }

}