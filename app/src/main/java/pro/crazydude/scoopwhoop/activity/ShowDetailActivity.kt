package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
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

        binding.viewModel = viewModel

        binding.retry.setOnClickListener {
            viewModel.haveInternet.postValue(true)
            viewModel.isLoading.postValue(true)
            bindData()
        }

        initRecycler()
        handlePagination()

        viewModel.topicSlug.value = intent.getStringExtra("topic_slug")


    }

    private fun handlePagination() {
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                viewModel.loadMore.postValue(true)

                when (viewModel.offset.value) {
                    -1 -> {
                        Log.i("TAG_1", "End of list")
                        viewModel.loadMore.postValue(false)
                    }
                    else -> {
                        viewModel.loadMore.postValue(true)
                        viewModel.loadShowDetail()
                    }
                }
            }
        })
    }

    private fun initRecycler() {
        binding.showVideosRecycler.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        showDetailAdapter = ShowDetailAdapter(showDetailDataList)
        showDetailAdapter.setHasStableIds(true)
        binding.showVideosRecycler.adapter = showDetailAdapter

    }

    private fun bindData() {

        viewModel.loadShowDetail()

    }


    private fun observeLiveData() {

        viewModel.showDetailsData.observe(this, {
            it.let {
                binding.model = it
                showDetailDataList.addAll(it.data)
                viewModel.currentOffset.postValue(viewModel.offset.value)
                viewModel.offset.postValue(it.next_offset)
                viewModel.isLoading.postValue(false)
                showDetailAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}