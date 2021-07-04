package pro.crazydude.scoopwhoop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ItemCarouselBinding
import pro.crazydude.scoopwhoop.model.Data

class CarouselAdapter(private val carouselList : ArrayList<Data>) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    class CarouselViewHolder(itemBinding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemCarouselBinding = itemBinding

        fun bindData(data: Data) {
            Glide.with(binding.carouselImage).load(data.feature_img).into(binding.carouselImage)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_carousel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bindData(carouselList[position])


    }

    override fun getItemCount(): Int {
        return carouselList.size
    }
}