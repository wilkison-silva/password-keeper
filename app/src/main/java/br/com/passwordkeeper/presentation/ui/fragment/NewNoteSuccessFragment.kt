package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentNewNoteSuccessBinding

import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewNoteSuccessFragment : Fragment(R.layout.fragment_new_note_success) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentNewNoteSuccessBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewNoteSuccessBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        lifecycleScope.launch {
            delay(2500)
            navController.popBackStack()
        }
    }

}