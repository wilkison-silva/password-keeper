package br.com.passwordkeeper.presentation.features.home

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.databinding.FragmentHomeBinding
import br.com.passwordkeeper.presentation.features.CurrentUserState
import br.com.passwordkeeper.presentation.features.MainViewModel
import br.com.passwordkeeper.presentation.features.home.states.GetAdviceState
import br.com.passwordkeeper.presentation.features.home.states.GetCategoriesSizeState
import br.com.passwordkeeper.presentation.features.home.states.GetFavoriteCardsState
import br.com.passwordkeeper.presentation.model.UserView
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
                }
            }
        }
    }

    private fun updateCurrentUser() {
        mainViewModel.updateCurrentUser()
    }

    private fun observeAdviceState() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.adviceState.collect {
                when (it) {
                    is GetAdviceState.Loading -> {
                        showHeaderShimmer()
                    }
                    is GetAdviceState.Success -> {
                        val adviceView = it.adviceView
                        binding.textViewMessage.text =
                            homeViewModel.getAdviceWithFirstLetterBold(adviceView)
                        binding.textViewTheAdviceAbove.text =
                            getString(R.string.the_advice_above, adviceView.quantityWords)
                        hideHeaderShimmer()
                    }
                    is GetAdviceState.SuccessWithoutMessage -> {
                        binding.textViewMessage.text = getString(R.string.no_message_found)
                        hideHeaderShimmer()
                    }
                    is GetAdviceState.ErrorUnknown -> {
                        binding.textViewMessage.text = getString(R.string.error)
                        hideHeaderShimmer()
                    }
                }
            }
        }
    }

    private fun hideHeaderShimmer() {
        binding.shimmerFrameLayout.visibility = GONE
        binding.constraintLayoutContentHeader.visibility = VISIBLE
    }

    private fun showHeaderShimmer() {
        binding.shimmerFrameLayout.visibility = VISIBLE
        binding.constraintLayoutContentHeader.visibility = INVISIBLE
    }

    private fun bindUserInfo(userView: UserView) {
        binding.textViewName.text = userView.name
        binding.textViewFirstLetterName.text = userView.firstCharacterName
    }

    private fun observeFavoriteCards() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.favoriteCardsState.collect {
                when (it) {
                    is GetFavoriteCardsState.ErrorUnknown -> {

                    }
                    is GetFavoriteCardsState.Success -> {
                        binding.progressBarLeft.visibility = GONE
                        binding.progressBarRight.visibility = GONE
                        val cardViewList = it.cardViewList
                        favoriteAdapter.updateList(cardViewList)
                        binding.constraintLayoutFavorite.visibility = VISIBLE
                        binding.constraintLayoutNoFavoriteYet.visibility = GONE
                    }
                    is GetFavoriteCardsState.NoElements -> {
                        binding.progressBarLeft.visibility = GONE
                        binding.progressBarRight.visibility = GONE
                        binding.constraintLayoutFavorite.visibility = GONE
                        binding.constraintLayoutNoFavoriteYet.visibility = VISIBLE
                    }
                    is GetFavoriteCardsState.Loading -> {
                        binding.progressBarLeft.visibility = VISIBLE
                        binding.progressBarRight.visibility = VISIBLE
                    }
                }
            }
        }

    }

    private fun observeCategoriesSize() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.categoriesSizeState.collect {
                when (it) {
                    is GetCategoriesSizeState.ErrorUnknown -> {

                    }
                    is GetCategoriesSizeState.Success -> {
                        binding.progressBarLeft.visibility = GONE
                        binding.progressBarRight.visibility = GONE
                        val categoriesViewList = it.categoriesViewList
                        categoryAdapter.updateList(categoriesViewList)
                        binding.constraintLayoutCategoriesSuccess.visibility = VISIBLE
                        binding.constraintLayoutNoCardsYet.visibility = GONE
                    }
                    is GetCategoriesSizeState.NoElements -> {
                        binding.progressBarLeft.visibility = GONE
                        binding.progressBarRight.visibility = GONE
                        binding.constraintLayoutCategoriesSuccess.visibility = GONE
                        binding.constraintLayoutNoCardsYet.visibility = VISIBLE
                    }
                    is GetCategoriesSizeState.Loading -> {
                        binding.progressBarLeft.visibility = VISIBLE
                        binding.progressBarRight.visibility = VISIBLE
                    }
                }
            }
        }
    }

    private fun updateCategoriesSizeState(email: String) {
        homeViewModel.updateCategoriesSize(email)
    }

    private fun setupCategoriesRecyclerView() {
        binding.recyclerViewCategories.adapter = categoryAdapter
        categoryAdapter.onClickItem = { categoryView ->
            navigateToListCardsFragment(categoryView.category)
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
            navigateToListCardsFragment(Categories.ALL)
        }
    }

    private fun navigateToListCardsFragment(category: Categories) {
        val directions =
            HomeFragmentDirections.actionFragmentHomeToFragmentListCards(
                category
            )
        navController.navigate(directions)
    }

}