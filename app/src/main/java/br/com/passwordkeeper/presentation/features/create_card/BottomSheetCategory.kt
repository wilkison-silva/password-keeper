package br.com.passwordkeeper.presentation.features.create_card

import android.content.Context
import android.os.Bundle
import br.com.passwordkeeper.databinding.BottomSheetCategoryBinding
import br.com.passwordkeeper.commons.Categories
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetCategory(
    context: Context,
    var onClickItem: (category: Categories) -> Unit
): BottomSheetDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding: BottomSheetCategoryBinding =BottomSheetCategoryBinding.inflate(layoutInflater)
            super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imageViewStreaming.setOnClickListener{
            onClickItem(Categories.STREAMING)
            dismiss()
        }
        binding.imageViewSocialMedia.setOnClickListener{
            onClickItem(Categories.SOCIAL_MEDIA)
            dismiss()
        }
        binding.imageViewBank.setOnClickListener{
            onClickItem(Categories.BANKS)
            dismiss()
        }
        binding.imageViewEducation.setOnClickListener{
            onClickItem(Categories.EDUCATION)
            dismiss()
        }
        binding.imageViewWork.setOnClickListener{
            onClickItem(Categories.WORK)
            dismiss()
        }
        binding.imageViewCard.setOnClickListener{
            onClickItem(Categories.CARD)
            dismiss()
        }
    }
}
