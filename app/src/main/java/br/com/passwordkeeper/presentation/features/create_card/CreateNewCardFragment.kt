package br.com.passwordkeeper.presentation.features.create_card

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.FragmentCreateNewCardBinding
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.commons.Categories.*
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateCardStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationCardStateResult
import br.com.passwordkeeper.presentation.features.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateNewCardFragment : Fragment(R.layout.fragment_create_new_card) {

    private val createNewCardViewModel: CreateNewCardViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    private val userView by lazy {
        val currentUserState = mainViewModel.currentUserState.value as CurrentUserState.Success
        currentUserState.userView
    }

    private lateinit var binding: FragmentCreateNewCardBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        binding = FragmentCreateNewCardBinding.bind(view)
        binding.toolbar.imageButtonBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.toolbar.textViewTitle.text = getString(R.string.create_a_new_card)
        updateCurrentUser()
        observeCurrentUser()
    }

    private fun updateCurrentUser() {
        mainViewModel.currentUserState
    }

    private fun observeCurrentUser() {
        mainViewModel.currentUserState.observe(viewLifecycleOwner) {
            when (it) {
                is CurrentUserState.ErrorUnknown -> {
                    navController.navigate(CreateNewCardFragmentDirections.actionNavigateToLoginFragment())
                }
                is CurrentUserState.Success -> {
                    setupCategoryTextEditInputText()
                    setupImageIconHeart()
                    setupCreateSaveCardButton()
                    observeFavoriteState()
                    observeCategorySelected()
                    observeFormValidation()
                    observeCreateCard()
                }
            }
        }
    }

    private fun setupCategoryTextEditInputText() {
        binding.textInputEditTextCategory.setOnClickListener {
            showBottomSheetDialogCategory()
        }
    }

    private fun showBottomSheetDialogCategory() {
        BottomSheetCategory(
            requireContext(),
            onClickItem = { category: Categories ->
                createNewCardViewModel.updateCategorySelected(category)
            }
        ).show()
    }

    private fun observeCategorySelected() {
        createNewCardViewModel.categorySelected.observe(viewLifecycleOwner) { category ->
            when (category) {
                STREAMING -> binding.textInputEditTextCategory.setText(R.string.streaming)
                SOCIAL_MEDIA -> binding.textInputEditTextCategory.setText(R.string.social_media)
                BANKS -> binding.textInputEditTextCategory.setText(R.string.banks)
                EDUCATION -> binding.textInputEditTextCategory.setText(R.string.education)
                WORK -> binding.textInputEditTextCategory.setText(R.string.work)
                CARD -> binding.textInputEditTextCategory.setText(R.string.cards)
                ALL -> { }
            }
        }
    }


    private fun setupImageIconHeart() {
        binding.imageViewIconHeart.setOnClickListener {
            createNewCardViewModel.updateFavorite()
        }
    }

    private fun observeFavoriteState() {
        createNewCardViewModel.favorite.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.imageViewIconHeart.setImageResource(R.drawable.ic_heart_full)
                false -> binding.imageViewIconHeart.setImageResource(R.drawable.ic_heart_empty)
            }
        }
    }

    private fun setupCreateSaveCardButton() {
        binding.buttonSave.setOnClickListener {
            val description = binding.textInputEditTextDescription.text.toString()
            val login = binding.textInputEditTextEmail.text.toString()
            val password = binding.textInputEditTextPassword.text.toString()
            val category = binding.textInputEditTextCategory.text.toString()
            val isFavorite = createNewCardViewModel.favorite.value ?: false

            createNewCardViewModel.validateForm(
                description = description,
                login = login,
                password = password,
                category = category,
                isFavorite = isFavorite,
                date = createNewCardViewModel.getCurrentDateTime()
            )
        }
    }

    private fun observeFormValidation() {
        createNewCardViewModel.formValidationCard.observe(viewLifecycleOwner) {
            when (it) {
                is FormValidationCardStateResult.CategoryNotSelected -> {
                    binding.textInputLayoutCategory.error =
                        getString(R.string.category_not_selected)
                }
                is FormValidationCardStateResult.DescriptionIsEmpty -> {
                    binding.textInputLayoutDescription.error =
                        getString(R.string.description_is_empty)
                }
                is FormValidationCardStateResult.Success -> {
                    createNewCardViewModel.createCard(
                        description = it.description,
                        login = it.login,
                        password = it.password,
                        isFavorite = it.isFavorite,
                        date = it.date,
                        emailUser = userView.email
                    )
                }
            }
        }
    }

    private fun observeCreateCard() {
        createNewCardViewModel.createCardState.observe(viewLifecycleOwner) {
            when (it) {
                is CreateCardStateResult.ErrorUnknown -> {
                    goToErrorNoteFragment()
                }
                is CreateCardStateResult.Success -> {
                    goToSuccessNewNoteFragment()
                }
                is CreateCardStateResult.Loading -> {
                    binding.buttonSave.text = ""
                    binding.progressBarSaving.visibility = VISIBLE
                }
            }
        }
    }

    private fun goToSuccessNewNoteFragment() {
        val directions =
            CreateNewCardFragmentDirections.actionFragmentCreateNewCardToFragmentNewCardSuccess()
        navController.navigate(directions)
    }

    private fun goToErrorNoteFragment() {
        val directions =
            CreateNewCardFragmentDirections.actionFragmentCreateNewCardToFragmentNewNoteError()
        navController.navigate(directions)
    }
}
