package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.adapter.ShowDetailAdapter
import pro.crazydude.scoopwhoop.databinding.ActivityShowDetailBinding
import pro.crazydude.scoopwhoop.model.ShowDetailData
import pro.crazydude.scoopwhoop.viewmodel.ShowDetailViewModel

class ShowDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ShowDetailViewModel
    private lateinit var binding: ActivityShowDetailBinding
    private lateinit var showDetailAdapter: ShowDetailAdapter
    private val showDetailDataList: ArrayList<ShowDetailData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ShowDetailViewModel::class.java)

        initViews()
        initToolbar()
        bindData()
        observeLiveData()
    }

    private fun initViews() {

        binding.showVideosRecycler.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        showDetailAdapter = ShowDetailAdapter(showDetailDataList)
        binding.showVideosRecycler.adapter = showDetailAdapter

    }

    private fun bindData() {
        val topicSlug = intent.getStringExtra("topic_slug")

        if (!topicSlug.isNullOrEmpty()) {
            viewModel.loadShowDetail(topicSlug)
        }
    }


    private fun observeLiveData() {

        viewModel.showDetailsData.observe(this, {
            it.let {
                binding.model = it
                showDetailDataList.addAll(it.data)
                showDetailAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun initToolbar() {

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        supportActionBar!!.title= "Show Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}