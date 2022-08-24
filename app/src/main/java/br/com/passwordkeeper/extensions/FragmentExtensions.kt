package br.com.passwordkeeper.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.com.passwordkeeper.databinding.DialogDownloadImageBinding

fun Fragment.downloadImageDialog(
    context: Context,
    onSaveURL: (url: String) -> Unit,
) {
    val binding: DialogDownloadImageBinding = DialogDownloadImageBinding.inflate(layoutInflater)

    AlertDialog.Builder(context)
        .setView(binding.root)
        .show().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            binding.buttonPreviewImage.setOnClickListener {
                val url = binding.textInputUrl.text.toString()
                binding.dialogImage.tryLoadImage(url)
            }
            binding.buttonSaveUrl.setOnClickListener {
                val url = binding.textInputUrl.text.toString()
                onSaveURL(url)
                dismiss()
            }
        }
}
