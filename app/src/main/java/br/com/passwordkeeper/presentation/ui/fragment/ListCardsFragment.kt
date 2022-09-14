package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.ListCardsFragmentBinding
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.ListCardsAdapter
import br.com.passwordkeeper.presentation.ui.viewmodel.ListCardsViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCardsFragment : Fragment(R.layout.list_cards_fragment) {

    private lateinit var binding: ListCardsFragmentBinding
    private val listCardsViewModel: ListCardsViewModel by viewModel()
    private val listCardsAdapter: ListCardsAdapter by inject()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val arguments by navArgs<ListCardsFragmentArgs>()

    private val title: Int by lazy {
        arguments.title
    }
    private val navController by lazy {
        findNavController()
    }

    private val userView by lazy {
        val currentUserState = listCardsViewModel.currentUserState.value as CurrentUserState.Success
        currentUserState.userView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListCardsFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        setTitle()
        updateCurrentUser()
        observeCurrentUserState()
    }


    private fun observeCurrentUserState() {
        listCardsViewModel.currentUserState.observe(viewLifecycleOwner) { currentUserState ->
            when (currentUserState) {
                is CurrentUserState.ErrorUnknown -> {
                    //DEVE VOLTAR PARA A TELA DE LOGIN
                }
                is CurrentUserState.Success -> {
                    setupListCardsRecyclerView()
                    setupButtonBack()
                    updateCards()
                    observeCards()
                }
            }
        }
    }

    private fun updateCurrentUser() {
        listCardsViewModel.updateCurrentUser()
    }

    private fun observeCards() {
        listCardsViewModel.allCards.observe(viewLifecycleOwner) {
            when (it) {
                is GetAllCardsStateResult.ErrorUnknown -> {

                }
                is GetAllCardsStateResult.Success -> {
                    binding.recyclerViewListCards.visibility = VISIBLE
                    binding.progressBar.visibility = GONE
                    val cardViewList = it.cardViewList
                    listCardsAdapter.updateList(cardViewList)

                }
                is GetAllCardsStateResult.Loading -> {
                    binding.recyclerViewListCards.visibility = GONE
                    binding.progressBar.visibility = VISIBLE
                }
            }
        }
    }

    private fun setupListCardsRecyclerView() {
        binding.recyclerViewListCards.adapter = listCardsAdapter
        listCardsAdapter.onClickItem = {

        }
    }

    private fun updateCards() {
        listCardsViewModel.updateCards(
            email = userView.email,
            titleRes = title,
            title = getString(title)
        )
    }

    private fun setTitle(){
        binding.textViewTitle.text = getString(title)
    }

    private fun setupButtonBack() {
        binding.imageButtonBackAllCategories.setOnClickListener {
            navController.popBackStack()
        }
    }

}
