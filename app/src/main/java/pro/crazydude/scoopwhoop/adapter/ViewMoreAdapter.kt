package pro.crazydude.scoopwhoop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ItemViewMoreBinding
import pro.crazydude.scoopwhoop.model.ViewMoreModel

class ViewMoreAdapter(private val viewMoreDataList: ArrayList<ViewMoreModel>) :
    RecyclerView.Adapter<ViewMoreAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemViewMoreBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemViewMoreBinding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view_more,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = viewMoreDataList[position]
    }

    override fun getItemCount(): Int {
        return viewMoreDataList.size
    }
}