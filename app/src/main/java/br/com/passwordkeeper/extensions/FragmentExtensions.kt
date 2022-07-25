package br.com.passwordkeeper.extensions

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showMessage(view: View, message: String) {
    Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .show()
}