package br.com.passwordkeeper.extensions

import android.widget.ImageView
import coil.load

fun ImageView.tryLoadImage(imageUrl: String? = null){
    load(imageUrl){
//        fallback(R.drawable.ic_image_fallback)
//        error(R.drawable.ic_image_error)
//        placeholder(R.drawable.ic_placeholder)
    }
}
