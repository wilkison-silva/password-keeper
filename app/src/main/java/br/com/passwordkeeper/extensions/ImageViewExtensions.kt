package br.com.passwordkeeper.extensions

import coil.load
import com.google.android.material.imageview.ShapeableImageView

fun ShapeableImageView.tryLoadImage(imageUrl: String? = null){
    load(imageUrl){
//        fallback(R.drawable.ic_image_fallback)
//        error(R.drawable.ic_image_error)
//        placeholder(R.drawable.ic_placeholder)
    }
}
