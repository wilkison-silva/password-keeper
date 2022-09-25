package br.com.passwordkeeper.presentation.features.list_cards

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentListCardsBinding
import br.com.passwordkeeper.commons.FiltersListCard
import br.com.passwordkeeper.presentation.features.CurrentUserState
import br.com.passwordkeeper.presentation.features.MainViewModel
import br.com.passwordkeeper.presentation.features.list_cards.states.GetCardsState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCardsFragment : Fragment(R.layout.fragment_list_cards) {

    private lateinit var binding: FragmentListCardsBinding
    private val listCardsViewModel: ListCardsViewModel by viewModel()
    private val listCardsAdapter: ListCardsAdapter by inject()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val arguments by navArgs<ListCardsFragmentArgs>()

    private val category by lazy {
        arguments.category
    }

    private val navController by lazy {
        findNavController()
    }

    private val userView by lazy {
        val currentUserState = mainViewModel.currentUserState.value as CurrentUserState.Success
        currentUserState.userView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListCardsBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setTitle()
        updateCurrentUser()
        observeCurrentUserState()
    }


    private fun observeCurrentUserState() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.currentUserState.collect { currentUserState ->
                when (currentUserState) {
                    is CurrentUserState.ErrorUnknown -> {
                        navController.navigate(ListCardsFragmentDirections.actionNavigateToLoginFragment())
                    }
                    is CurrentUserState.Success -> {
                        setupListCardsRecyclerView()
                        setupButtonBack()
                        updateCards(filter = FiltersListCard.DESCRIPTION)
                        observeCards()
                        setupButtonDescription()
                        setupButtonDate()
                        setupButtonCategory()
                        setupButtonFavorites()
                    }
                    is CurrentUserState.EmptyState -> { }
                }
            }
        }
    }

    private fun updateCurrentUser() {
        mainViewModel.updateCurrentUser()
    }

    private fun observeCards() {
        lifecycleScope.launchWhenStarted {
            listCardsViewModel.cardsListState.collect {
                when (it) {
                    is GetCardsState.ErrorUnknown -> {

                    }
                    is GetCardsState.Success -> {
                        binding.recyclerViewListCards.visibility = VISIBLE
                        binding.progressBar.visibility = GONE
                        val cardViewList = it.cardViewList
                        listCardsAdapter.updateList(cardViewList)

                    }
                    is GetCardsState.Loading -> {
                        binding.recyclerViewListCards.visibility = GONE
                        binding.progressBar.visibility = VISIBLE
                    }
                    is GetCardsState.EmptyState -> {}
                }
            }
        }
    }

    private fun setupListCardsRecyclerView() {
        binding.recyclerViewListCards.adapter = listCardsAdapter
        listCardsAdapter.onClickItem = {

        }
    }

    private fun updateCards(filter: FiltersListCard) {
        listCardsViewModel.updateCards(
            email = userView.email,
            category = category,
            filter = filter,
        )
    }

    private fun setTitle() {
        val stringRes = listCardsViewModel.getTitle(category)
        binding.toolbar.textViewTitle.text = getString(stringRes)
    }

    private fun setupButtonBack() {
        binding.toolbar.imageButtonBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun setupButtonDescription() {
        binding.filterSortBar.filterDescription.setOnClickListener {
         updateCards(filter = FiltersListCard.DESCRIPTION)
        }
    }

    private fun setupButtonDate() {
        binding.filterSortBar.filterDate.setOnClickListener {
            updateCards(filter = FiltersListCard.DATE)
        }
    }

    private fun setupButtonCategory() {
        binding.filterSortBar.filterCategory.setOnClickListener {
            updateCards(filter = FiltersListCard.CATEGORY)
        }
    }

    private fun setupButtonFavorites() {
        binding.filterSortBar.filterFavorites.setOnClickListener {
            updateCards(filter = FiltersListCard.FAVORITES)
        }
    }
}
