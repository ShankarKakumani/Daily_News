package pro.crazydude.scoopwhoop.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.activity.ShowDetailActivity
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

        holder.binding.cardView.apply {

            setOnClickListener {

                val intent = Intent(it.context, ShowDetailActivity::class.java)
                intent.putExtra("topic_slug", viewMoreDataList[position].topicSlug)
                it.context.startActivity(intent)

            }
        }
    }

    override fun getItemCount(): Int {
        return viewMoreDataList.size
    }
}