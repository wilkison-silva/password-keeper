package br.com.passwordkeeper.presentation.ui.dialog

import android.content.Context
import android.os.Bundle
import br.com.passwordkeeper.databinding.BottomSheetCategoryBinding
import br.com.passwordkeeper.domain.model.Categories
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
            onClickItem(Categories.STREAMING_TYPE)
            dismiss()
        }
        binding.imageViewSocialMedia.setOnClickListener{
            onClickItem(Categories.SOCIAL_MEDIA_TYPE)
            dismiss()
        }
        binding.imageViewBank.setOnClickListener{
            onClickItem(Categories.BANKS_TYPE)
            dismiss()
        }
        binding.imageViewEducation.setOnClickListener{
            onClickItem(Categories.EDUCATION_TYPE)
            dismiss()
        }
        binding.imageViewWork.setOnClickListener{
            onClickItem(Categories.WORK_TYPE)
            dismiss()
        }
        binding.imageViewCard.setOnClickListener{
            onClickItem(Categories.CARD_TYPE)
            dismiss()
        }
    }
}
