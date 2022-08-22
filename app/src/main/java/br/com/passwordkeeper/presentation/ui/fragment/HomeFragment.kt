package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetCategoriesSizeStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetFavoriteCardsStateResult
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.CategoryAdapter
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.FavoriteAdapter
import br.com.passwordkeeper.presentation.ui.viewmodel.HomeViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val navController by lazy {
        findNavController()
    }

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val categoryAdapter: CategoryAdapter by inject()
    private val favoriteAdapter: FavoriteAdapter by inject()
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = true)
        subscribeObservers()
        updateObservers()
        setupComponents()
    }

    private fun subscribeObservers() {
        observeCurrentUserState()
        observeAdviceState()
        observeCategoriesSize()
        observeFavoriteCards()
    }

    private fun updateObservers() {
        updateCurrentUser()
        updateAdviceState()
    }

    private fun setupComponents() {
        setupCategoriesRecyclerView()
        setupFavoriteRecyclerView()
    }

    private fun observeCurrentUserState() {
        homeViewModel.currentUserState.observe(viewLifecycleOwner) { currentUserState ->
            when (currentUserState) {
                is CurrentUserState.ErrorUnknown -> {

                }
                is CurrentUserState.Success -> {
                    bindUserInfo(currentUserState.userView)
                    updateCategoriesSizeState(currentUserState.userView.email)
                    updateFavorites(currentUserState.userView.email)
                }
            }
        }
    }

    private fun updateCurrentUser() {
        homeViewModel.updateCurrentUser()
    }

    private fun observeAdviceState() {
        homeViewModel.adviceState.observe(viewLifecycleOwner) {
            when (it) {
                is GetAdviceStateResult.Loading -> {
                    binding.textViewMessage.text = getString(R.string.loading)
                    binding.firstLetter.text = ""
                }
                is GetAdviceStateResult.Success -> {
                    val adviceView = it.adviceView
                    binding.textViewMessage.text = adviceView.advice
                    binding.textViewTheAdviceAbove.text =
                        getString(R.string.the_advice_above, adviceView.quantityWords)
                    binding.firstLetter.text = adviceView.firstLetter
                }
                is GetAdviceStateResult.SuccessWithoutMessage -> {
                    binding.textViewMessage.text = getString(R.string.no_message_found)
                }
                is GetAdviceStateResult.ErrorUnknown -> {
                    binding.textViewMessage.text = getString(R.string.error)
                }
            }
        }
    }

    private fun updateAdviceState() {
        homeViewModel.updateAdvice()
    }

    private fun bindUserInfo(userView: UserView) {
        binding.textViewName.text = userView.name
        binding.textViewFirstLetterName.text = userView.firstCharacterName
    }

    private fun observeFavoriteCards() {
        homeViewModel.favoriteCardsState.observe(viewLifecycleOwner) {
            when(it) {
                is GetFavoriteCardsStateResult.ErrorUnknown -> {}
                is GetFavoriteCardsStateResult.Success -> {
                   val cardViewList = it.cardViewList
                    favoriteAdapter.updateList(cardViewList)
                    binding.constraintLayoutFavorite.visibility = VISIBLE
                    binding.constraintLayoutNoFavoriteYet.visibility = GONE
                }
                GetFavoriteCardsStateResult.NoElements -> {
                    binding.constraintLayoutFavorite.visibility = GONE
                    binding.constraintLayoutNoFavoriteYet.visibility = VISIBLE
                }
            }
        }

    }

    private fun observeCategoriesSize() {
        homeViewModel.categoriesSizeState.observe(viewLifecycleOwner) {
            when(it) {
                is GetCategoriesSizeStateResult.ErrorUnknown -> {}
                is GetCategoriesSizeStateResult.Success -> {
                    val categoriesViewList = it.categoriesViewList
                    categoryAdapter.updateList(categoriesViewList)
                    binding.constraintLayoutCategoriesSuccess.visibility = VISIBLE
                    binding.constraintLayoutNoCardsYet.visibility = GONE
                }
                is GetCategoriesSizeStateResult.NoElements -> {
                    binding.constraintLayoutCategoriesSuccess.visibility = GONE
                    binding.constraintLayoutNoCardsYet.visibility = VISIBLE
                }
            }
        }
    }

    private fun updateCategoriesSizeState(email: String) {
        homeViewModel.updateCategoriesSize(email)
    }

    private fun setupCategoriesRecyclerView() {
        binding.recyclerViewTypes.adapter = categoryAdapter
    }

   private fun updateFavorites(email: String) {
       homeViewModel.updateFavoriteCards(email)
   }

    private fun setupFavoriteRecyclerView() {
        binding.recyclerViewFavorite.adapter = favoriteAdapter
    }
}