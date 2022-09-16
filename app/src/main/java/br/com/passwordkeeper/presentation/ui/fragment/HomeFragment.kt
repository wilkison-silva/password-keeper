package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentHomeBinding
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


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val navController by lazy {
        findNavController()
    }

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val categoryAdapter: CategoryAdapter by inject()
    private val favoriteAdapter: FavoriteAdapter by inject()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = true)
        observeCurrentUserState()
        updateCurrentUser()
    }

    private fun setupComponents() {
        setupCategoriesRecyclerView()
        setupFavoriteRecyclerView()
    }

    private fun observeCurrentUserState() {
        mainViewModel.currentUserState.observe(viewLifecycleOwner) { currentUserState ->
            when (currentUserState) {
                is CurrentUserState.ErrorUnknown -> {
                    navController.navigate(HomeFragmentDirections.actionNavigateToLoginFragment())
                }
                is CurrentUserState.Success -> {
                    bindUserInfo(currentUserState.userView)
                    observeAdviceState()
                    observeFavoriteCards()
                    observeCategoriesSize()
                    setupComponents()
                    setupTextViewOnClick()
                    updateCategoriesSizeState(currentUserState.userView.email)
                    updateFavorites(currentUserState.userView.email)
                    updateAdviceState()
                }
            }
        }
    }

    private fun updateCurrentUser() {
        mainViewModel.updateCurrentUser()
    }

    private fun observeAdviceState() {
        homeViewModel.adviceState.observe(viewLifecycleOwner) {
            when (it) {
                is GetAdviceStateResult.Loading -> {
                    binding.textViewMessage.text = getString(R.string.loading)
                }
                is GetAdviceStateResult.Success -> {
                    val adviceView = it.adviceView
                    binding.textViewMessage.text =
                        homeViewModel.getAdviceWithFirstLetterBold(adviceView)
                    binding.textViewTheAdviceAbove.text =
                        getString(R.string.the_advice_above, adviceView.quantityWords)
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
            when (it) {
                is GetFavoriteCardsStateResult.ErrorUnknown -> {

                }
                is GetFavoriteCardsStateResult.Success -> {
                    binding.progressBarLeft.visibility = GONE
                    binding.progressBarRight.visibility = GONE
                    val cardViewList = it.cardViewList
                    favoriteAdapter.updateList(cardViewList)
                    binding.constraintLayoutFavorite.visibility = VISIBLE
                    binding.constraintLayoutNoFavoriteYet.visibility = GONE
                }
                is GetFavoriteCardsStateResult.NoElements -> {
                    binding.progressBarLeft.visibility = GONE
                    binding.progressBarRight.visibility = GONE
                    binding.constraintLayoutFavorite.visibility = GONE
                    binding.constraintLayoutNoFavoriteYet.visibility = VISIBLE
                }
                is GetFavoriteCardsStateResult.Loading -> {
                    binding.progressBarLeft.visibility = VISIBLE
                    binding.progressBarRight.visibility = VISIBLE
                }
            }
        }

    }

    private fun observeCategoriesSize() {
        homeViewModel.categoriesSizeState.observe(viewLifecycleOwner) {
            when (it) {
                is GetCategoriesSizeStateResult.ErrorUnknown -> {

                }
                is GetCategoriesSizeStateResult.Success -> {
                    binding.progressBarLeft.visibility = GONE
                    binding.progressBarRight.visibility = GONE
                    val categoriesViewList = it.categoriesViewList
                    categoryAdapter.updateList(categoriesViewList)
                    binding.constraintLayoutCategoriesSuccess.visibility = VISIBLE
                    binding.constraintLayoutNoCardsYet.visibility = GONE
                }
                is GetCategoriesSizeStateResult.NoElements -> {
                    binding.progressBarLeft.visibility = GONE
                    binding.progressBarRight.visibility = GONE
                    binding.constraintLayoutCategoriesSuccess.visibility = GONE
                    binding.constraintLayoutNoCardsYet.visibility = VISIBLE
                }
                is GetCategoriesSizeStateResult.Loading -> {
                    binding.progressBarLeft.visibility = VISIBLE
                    binding.progressBarRight.visibility = VISIBLE
                }
            }
        }
    }

    private fun updateCategoriesSizeState(email: String) {
        homeViewModel.updateCategoriesSize(email)
    }

    private fun setupCategoriesRecyclerView() {
        binding.recyclerViewCategories.adapter = categoryAdapter
        categoryAdapter.onClickItem = {
            when (it.nameAsStringRes) {
                R.string.streaming -> navigateToListCardsFragment(R.string.streaming)
                R.string.social_media -> navigateToListCardsFragment(R.string.social_media)
                R.string.banks -> navigateToListCardsFragment(R.string.banks)
                R.string.education -> navigateToListCardsFragment(R.string.education)
                R.string.work -> navigateToListCardsFragment(R.string.work)
                R.string.cards -> navigateToListCardsFragment(R.string.cards)
            }
        }
    }

    private fun updateFavorites(email: String) {
        homeViewModel.updateFavoriteCards(email)
    }

    private fun setupFavoriteRecyclerView() {
        binding.recyclerViewFavorite.adapter = favoriteAdapter
    }

    private fun setupTextViewOnClick() {
        binding.TextViewViewAll.setOnClickListener {
            navigateToListCardsFragment(R.string.title_all_categories)
        }
    }

    private fun navigateToListCardsFragment(@StringRes title: Int) {
        val directions =
            HomeFragmentDirections.actionFragmentHomeToFragmentListCards(title)
        navController.navigate(directions)
    }

}