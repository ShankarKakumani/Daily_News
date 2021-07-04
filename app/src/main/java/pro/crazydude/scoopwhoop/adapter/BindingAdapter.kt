package pro.crazydude.scoopwhoop.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pro.crazydude.scoopwhoop.R

object BindingAdapter {

    @BindingAdapter(value = ["app:loadImage"])
    @JvmStatic
    fun setDrawableImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .error(R.drawable.placeholder_rectangle)
            .placeholder(R.drawable.placeholder)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(view)
    }

}