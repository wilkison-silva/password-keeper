package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.ListCardsFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetCardsByCategoryStateResult
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.ListCardsAdapter
import br.com.passwordkeeper.presentation.ui.viewmodel.ListCardsViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCardsFragment: Fragment(R.layout.list_cards_fragment) {

    private lateinit var binding: ListCardsFragmentBinding
    private val listCardsViewModel: ListCardsViewModel by viewModel()
    private val listCardsAdapter: ListCardsAdapter by inject()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val arguments by navArgs<ListCardsFragmentArgs>()
    private val email by lazy {
        arguments.email
    }
    private val title by lazy {
        arguments.title
    }
    private val navController by lazy {
        findNavController()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListCardsFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setupListCardsRecyclerView()
        observeCards()
        updateCategories()
        setupButtonBack()
        observeCardsByCategory()
    }

    private fun observeCards() {
        listCardsViewModel.allCards.observe(viewLifecycleOwner) {
            when(it) {
                is GetAllCardsStateResult.ErrorUnknown -> {}
                is GetAllCardsStateResult.Success -> {
                    val cardViewList = it.cardViewList
                   listCardsAdapter.updateList(cardViewList)
                }
            }
        }
    }

    private fun setupListCardsRecyclerView() {
        listCardsAdapter.onClickItem = {
            binding.recyclerViewListCards.adapter = listCardsAdapter
        }

    }

    private fun updateCategories() {
        if (title == "View all") {
            binding.textViewTitle.text = getString(R.string.title_all_categories)
        }
        if (title == "Streaming") {
            binding.textViewTitle.text = getString(R.string.title_streaming)
        }
        if (title == "Social media") {
            binding.textViewTitle.text = getString(R.string.title_social_media)
        }
        if (title == "Banks") {
            binding.textViewTitle.text = getString(R.string.title_banks)
        }
        if (title == "Education") {
            binding.textViewTitle.text = getString(R.string.title_education)
        }
        if (title == "Work") {
            binding.textViewTitle.text = getString(R.string.title_work)
        }
        if (title == "Cards") {
            binding.textViewTitle.text = getString(R.string.title_cards)
        }
        listCardsViewModel.updateCards(email, title)
        listCardsViewModel.updateCardsByCategory(category = "Streaming", email)
    }

    private fun setupButtonBack() {
        binding.imageButtonBackAllCategories.setOnClickListener{
           navController.popBackStack()
        }
    }

    private fun observeCardsByCategory() {
        listCardsViewModel.cardsByCategoryState.observe(viewLifecycleOwner) {
            when(it) {
                is GetCardsByCategoryStateResult.ErrorUnknown -> {}
                is GetCardsByCategoryStateResult.Success -> {
                    val cardViewList = it.cardViewList
                    listCardsAdapter.updateList(cardViewList)
                    binding.recyclerViewListCards.visibility = VISIBLE
                }
            }
        }
    }

//    private fun observeCardsByCategoryState(category: String, email: String) {
//        listCardsViewModel.updateCardsByCategory(category, email)
//    }
}
