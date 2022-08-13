package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FormCardFragmentBinding
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FormCardFragment : Fragment(R.layout.form_card_fragment) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: FormCardFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.updateBottomNavigationVisibility(visibility = true)
        binding = FormCardFragmentBinding.bind(view)
        binding.imageButtonBack.setOnClickListener {
            navController.popBackStack()
        }
    }
}