package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
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

        binding.showVideosRecycler.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        showDetailAdapter = ShowDetailAdapter(showDetailDataList)
        showDetailAdapter.setHasStableIds(true);
        binding.showVideosRecycler.adapter = showDetailAdapter

        viewModel.topicSlug.value = intent.getStringExtra("topic_slug")

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {

                when (viewModel.offset.value) {
                    -1 -> {
                        Toast.makeText(this, "End of list ", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        viewModel.loadShowDetail()
                    }
                }
            }
        })
    }

    private fun bindData() {

        viewModel.loadShowDetail()

    }


    private fun observeLiveData() {

        viewModel.showDetailsData.observe(this, {
            it.let {
                binding.model = it
                showDetailDataList.addAll(it.data)
                viewModel.offset.postValue(it.next_offset)
                viewModel.isLoading.postValue(false)
                showDetailAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun initToolbar() {

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        supportActionBar!!.title = "Show Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}