package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.databinding.AllCategoriesFragmentBinding
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllCategoriesFragment: Fragment() {

    private lateinit var binding: AllCategoriesFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val arguments by navArgs<AllCategoriesFragmentArgs>()
    private val email by lazy {
        arguments.email
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AllCategoriesFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
    }
}