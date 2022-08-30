package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.NewNoteSuccessBinding
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewNoteSuccessFragment : Fragment(R.layout.new_note_success) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: NewNoteSuccessBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewNoteSuccessBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
    }

}