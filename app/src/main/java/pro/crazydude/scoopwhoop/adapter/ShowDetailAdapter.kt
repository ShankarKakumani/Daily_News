package pro.crazydude.scoopwhoop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ItemShowDetailBinding
import pro.crazydude.scoopwhoop.model.ShowDetailData

class ShowDetailAdapter(private val dataList: ArrayList<ShowDetailData>) : RecyclerView.Adapter<ShowDetailAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemShowDetailBinding) :

        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemShowDetailBinding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_show_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = dataList[position]

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}