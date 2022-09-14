package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.CreateNewCardFragmentBinding
import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.model.Categories.*
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateCardStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationCardStateResult
import br.com.passwordkeeper.presentation.ui.dialog.BottomSheetCategory
import br.com.passwordkeeper.presentation.ui.viewmodel.CreateNewCardViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateNewCardFragment : Fragment(R.layout.create_new_card_fragment) {

    private val createNewCardViewModel: CreateNewCardViewModel by viewModel()

    private val navController by lazy {
        findNavController()
    }

    private val userView by lazy {
        val currentUserState = mainViewModel.currentUserState.value as CurrentUserState.Success
        currentUserState.userView
    }

    private lateinit var binding: CreateNewCardFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.updateBottomNavigationVisibility(visibility = false)
        binding = CreateNewCardFragmentBinding.bind(view)
        binding.imageButtonBack.setOnClickListener {
            navController.popBackStack()
        }
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
                when (category) {
                    STREAMING_TYPE -> binding.textInputEditTextCategory.setText(R.string.streaming)
                    SOCIAL_MEDIA_TYPE -> binding.textInputEditTextCategory.setText(R.string.social_media)
                    BANKS_TYPE -> binding.textInputEditTextCategory.setText(R.string.banks)
                    EDUCATION_TYPE -> binding.textInputEditTextCategory.setText(R.string.education)
                    WORK_TYPE -> binding.textInputEditTextCategory.setText(R.string.work)
                    CARD_TYPE -> binding.textInputEditTextCategory.setText(R.string.cards)
                }
            }
        ).show()
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
                    val userView = userView
                    createNewCardViewModel.createCard(
                        description = it.description,
                        login = it.login,
                        password = it.password,
                        category = it.category,
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
            CreateNewCardFragmentDirections.actionFormCardFragmentToNewNoteSuccessFragment()
        navController.navigate(directions)
    }

    private fun goToErrorNoteFragment() {
        val directions =
            CreateNewCardFragmentDirections.actionFormCardFragmentToNewNoteErrorFragment()
        navController.navigate(directions)
    }
}
