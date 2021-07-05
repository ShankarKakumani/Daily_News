package pro.crazydude.scoopwhoop.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.activity.ShowDetailActivity
import pro.crazydude.scoopwhoop.databinding.ItemTopShowsBinding
import pro.crazydude.scoopwhoop.model.TopShowsData

class TopShowsAdapter(private val dataList: ArrayList<TopShowsData>) : RecyclerView.Adapter<TopShowsAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemTopShowsBinding) :

        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemTopShowsBinding = itemBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_top_shows,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.model = dataList[position]
        holder.binding.movieLayout.apply {
            setOnClickListener {
                val intent = Intent(it.context, ShowDetailActivity::class.java)
                intent.putExtra("topic_slug", dataList[position].topic_slug)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}