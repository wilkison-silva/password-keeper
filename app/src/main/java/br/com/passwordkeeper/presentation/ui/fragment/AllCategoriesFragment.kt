package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.AllCategoriesFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.AllCategoriesAdapter
import br.com.passwordkeeper.presentation.ui.viewmodel.AllCategoriesViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCategoriesFragment: Fragment(R.layout.all_categories_fragment) {

    private lateinit var binding: AllCategoriesFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val arguments by navArgs<AllCategoriesFragmentArgs>()
    private val allCategoriesViewModel: AllCategoriesViewModel by viewModel()
    private val allCategoriesAdapter: AllCategoriesAdapter by inject()
    private val email by lazy {
        arguments.email
    }
    private val navController by lazy {
        findNavController()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AllCategoriesFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setupAllFavoritesRecyclerView()
        observeCards()
        updateCategories()
        setupButtonBack()
    }

    private fun observeCards() {
        allCategoriesViewModel.allCards.observe(viewLifecycleOwner) {
            when(it) {
                is GetAllCardsStateResult.ErrorUnknown -> {}
                is GetAllCardsStateResult.Success -> {
                    val cardViewList = it.cardViewList
                    allCategoriesAdapter.updateList(cardViewList)
                }
            }
        }
    }

    private fun setupAllFavoritesRecyclerView() {
        binding.recyclerViewAllCategories.adapter = allCategoriesAdapter
    }

    private fun updateCategories() {
        allCategoriesViewModel.updateCards(email)
    }

    private fun setupButtonBack() {
        binding.imageButtonBackAllCategories.setOnClickListener{
           navController.popBackStack()
        }
    }
}
