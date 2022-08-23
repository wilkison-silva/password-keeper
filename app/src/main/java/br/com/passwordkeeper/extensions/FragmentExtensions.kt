package br.com.passwordkeeper.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.com.passwordkeeper.R
import com.google.android.material.button.MaterialButton

fun Fragment.downloadImageDialog(
    context: Context,
    saveUrl: () -> Unit,
    previewImage: () -> Unit = {},
) {
    AlertDialog.Builder(context)
        .setView(R.layout.dialog_download_image)
        .show().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val confirmButton = findViewById<MaterialButton>(R.id.buttonSaveUrl)
            confirmButton?.setOnClickListener {
                saveUrl()
                dismiss()
            }
            val dismissButton = findViewById<MaterialButton>(R.id.buttonPreviewImage)
            dismissButton?.setOnClickListener {
                previewImage()
                dismiss()
            }
        }
}
