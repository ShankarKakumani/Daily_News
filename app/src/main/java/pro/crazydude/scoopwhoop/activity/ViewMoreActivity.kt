package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.adapter.ViewMoreAdapter
import pro.crazydude.scoopwhoop.databinding.ActivityViewMoreBinding
import pro.crazydude.scoopwhoop.model.ViewMoreModel
import pro.crazydude.scoopwhoop.util.Constants.EDITORS_PICK_URL
import pro.crazydude.scoopwhoop.util.Constants.LATEST_URL
import pro.crazydude.scoopwhoop.util.Constants.TOP_SHOWS_URL
import pro.crazydude.scoopwhoop.viewmodel.MainActivityViewModel

class ViewMoreActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityViewMoreBinding

    private val viewMoreDataList: ArrayList<ViewMoreModel> = ArrayList()

    private lateinit var viewMoreAdapter: ViewMoreAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        initViews()
        initToolbar()
        handleIntentData()
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.latestData.observe(this, {

            it.let {

                for (data in it.data) {
                    viewMoreDataList.add(
                        ViewMoreModel(
                            data.feature_img,
                            data.title,
                            data.show.topic_display.topic_slug
                        )
                    )
                }
                viewMoreAdapter.notifyDataSetChanged()
            }
        })

        viewModel.editorsPickData.observe(this, {

            it.let {

                for (data in it.data) {
                    viewMoreDataList.add(
                        ViewMoreModel(
                            data.feature_img,
                            data.title,
                            data.show.topic_display.topic_slug
                        )
                    )
                }
                viewMoreAdapter.notifyDataSetChanged()
            }
        })

        viewModel.topShowsData.observe(this, {

            it.let {

                for (data in it.data) {
                    viewMoreDataList.add(
                        ViewMoreModel(
                            data.feature_img_port,
                            data.topic_name,
                            data.topic_slug
                        )
                    )
                }
                viewMoreAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initViews() {
        binding.viewmodel = viewModel

        vmRecycler()
    }

    private fun vmRecycler() {

        binding.vmRecycler.layoutManager =
            GridLayoutManager(
                this,
                2,
                GridLayoutManager.VERTICAL,
                false
            )


        viewMoreAdapter = ViewMoreAdapter(viewMoreDataList)
        binding.vmRecycler.adapter = viewMoreAdapter
    }

    private fun handleIntentData() {

        val apiUrl = intent.getStringExtra("apiUrl")

        apiUrl.let {

            when (apiUrl) {

                LATEST_URL -> {
                    viewModel.loadLatest()
                    viewModel.toolbarTitle.postValue(resources.getString(R.string.latest))
                }

                EDITORS_PICK_URL -> {
                    viewModel.loadEditorsPick()
                    viewModel.toolbarTitle.postValue(resources.getString(R.string.editors_pick))
                }

                TOP_SHOWS_URL -> {
                    viewModel.loadTopShows()
                    viewModel.toolbarTitle.postValue(resources.getString(R.string.top_shows))
                }

            }
        }
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