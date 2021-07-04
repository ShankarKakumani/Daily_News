package pro.crazydude.scoopwhoop.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ItemVideoPrortaitBinding
import pro.crazydude.scoopwhoop.model.LatestData

class LatestAdapter(private val dataList: ArrayList<LatestData>) : RecyclerView.Adapter<LatestAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemVideoPrortaitBinding) :

        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemVideoPrortaitBinding = itemBinding

        fun bindData(data: LatestData) {
            Glide.with(binding.movieImageView)
                .load(data.video_thumbnail_2x3)
                .error(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .addListener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.movieNameView.visibility = View.VISIBLE
                        binding.movieProgressBar.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.movieNameView.visibility = View.GONE
                        binding.movieProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.movieImageView)


//            Glide.with(binding.carouselImage).load(data.feature_img).into(binding.carouselImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_video_prortait,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}