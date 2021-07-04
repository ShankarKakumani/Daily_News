package pro.crazydude.scoopwhoop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.databinding.ItemEditorsPickBinding
import pro.crazydude.scoopwhoop.model.EditorsPickData

class EditorsPickAdapter(private val dataList: ArrayList<EditorsPickData>) : RecyclerView.Adapter<EditorsPickAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemEditorsPickBinding) :

        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemEditorsPickBinding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_editors_pick,
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