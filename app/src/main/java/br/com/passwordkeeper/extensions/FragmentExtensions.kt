package br.com.passwordkeeper.extensions

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.DialogViewImageBinding
import br.com.passwordkeeper.presentation.model.CardView

fun Fragment.visualizeCardDialog(
    context: Context,
    cardView: CardView
) {
    val binding: DialogViewImageBinding = DialogViewImageBinding.inflate(layoutInflater)
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true

    AlertDialog.Builder(context)
        .setView(binding.root)
        .show().apply {
            binding.includeCardViewFront.textViewTitle.text = cardView.description
            binding.includeCardViewFront.textViewType.text = context.getString(cardView.category)
            binding.includeCardViewFront.textViewDate.text = cardView.dateAsString
            binding.includeCardViewFront.imageViewHeart.setImageResource(cardView.iconHeart)

            binding.includeCardViewBack.textViewLoginValue.text = cardView.login
            binding.includeCardViewBack.textViewPasswordValue.text = cardView.password

            val scale = context.resources.displayMetrics.density
            val front = binding.includeCardViewFront.cardViewFront
            val back = binding.includeCardViewBack.cardViewBack

            front.cameraDistance = 8000 * scale
            back.cameraDistance = 8000 * scale

            front_anim =
                AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
            back_anim =
                AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet

            front.setOnClickListener {
                if (isFront) {
                    front_anim.setTarget(front);
                    back_anim.setTarget(back);
                    front_anim.start()
                    back_anim.start()
                    isFront = false

                } else {
                    front_anim.setTarget(back)
                    back_anim.setTarget(front)
                    back_anim.start()
                    front_anim.start()
                    isFront = true

                }
            }

            back.setOnClickListener {
                if (isFront) {
                    front_anim.setTarget(front);
                    back_anim.setTarget(back);
                    front_anim.start()
                    back_anim.start()
                    isFront = false

                } else {
                    front_anim.setTarget(back)
                    back_anim.setTarget(front)
                    back_anim.start()
                    front_anim.start()
                    isFront = true

                }
            }
        }
}
