package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.CreateNewCardFragmentBinding
import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.model.Categories.*
import br.com.passwordkeeper.extensions.downloadImageDialog
import br.com.passwordkeeper.presentation.ui.dialog.BottomSheetCategory
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateNewCardFragment : Fragment(R.layout.create_new_card_fragment) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: CreateNewCardFragmentBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.updateBottomNavigationVisibility(visibility = true)
        binding = CreateNewCardFragmentBinding.bind(view)
        binding.imageButtonBack.setOnClickListener {
            navController.popBackStack()
        }
        showBottomSheet()
        showDialogDownloadImage()
    }

    private fun showBottomSheet() {
        binding.textInputEditTextCategory.setOnClickListener {
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
    }

    private fun showDialogDownloadImage() {
        binding.imageViewCard.setOnClickListener {
            downloadImageDialog(
                requireContext(),
                onSaveURL = { url: String ->
                    Log.i("Testando", "showDialogDownloadImage: $url")
                })
        }
    }
}