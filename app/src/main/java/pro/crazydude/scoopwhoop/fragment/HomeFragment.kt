package pro.crazydude.scoopwhoop.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.animations.DescriptionAnimation
import com.glide.slider.library.slidertypes.BaseSliderView
import com.glide.slider.library.slidertypes.TextSliderView
import com.glide.slider.library.tricks.ViewPagerEx
import pro.crazydude.scoopwhoop.R
import pro.crazydude.scoopwhoop.activity.ShowDetailActivity
import pro.crazydude.scoopwhoop.activity.ViewMoreActivity
import pro.crazydude.scoopwhoop.adapter.EditorsPickAdapter
import pro.crazydude.scoopwhoop.adapter.LatestAdapter
import pro.crazydude.scoopwhoop.adapter.TopShowsAdapter
import pro.crazydude.scoopwhoop.databinding.FragmentHomeBinding
import pro.crazydude.scoopwhoop.model.Carousel
import pro.crazydude.scoopwhoop.model.EditorsPickData
import pro.crazydude.scoopwhoop.model.LatestData
import pro.crazydude.scoopwhoop.model.TopShowsData
import pro.crazydude.scoopwhoop.util.Constants.EDITORS_PICK_URL
import pro.crazydude.scoopwhoop.util.Constants.LATEST_URL
import pro.crazydude.scoopwhoop.util.Constants.TOP_SHOWS_URL
import pro.crazydude.scoopwhoop.viewmodel.MainActivityViewModel


class HomeFragment : Fragment(), BaseSliderView.OnSliderClickListener,
    ViewPagerEx.OnPageChangeListener {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var imageSlider: SliderLayout

    private val carouselImagesList: ArrayList<Carousel> = ArrayList()
    private val latestDataList: ArrayList<LatestData> = ArrayList()
    private val editorsPickDataList: ArrayList<EditorsPickData> = ArrayList()
    private val topShowsDataList: ArrayList<TopShowsData> = ArrayList()

    private lateinit var latestAdapter: LatestAdapter
    private lateinit var editorsPickAdapter: EditorsPickAdapter
    private lateinit var topShowsAdapter: TopShowsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.clickHandler = ClickHandler()
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        initViews()
        bindData()
        observeLiveData()
        return binding.root
    }


    private fun initViews() {
        binding.viewModel = viewModel

        binding.retry.setOnClickListener {
            viewModel.haveInternet.postValue(true)
            viewModel.isLoading.postValue(true)
            bindData()
        }

        latestRecycler()
        editorPicks()
        topShows()

    }

    private fun bindData() {
        viewModel.loadAllData()
    }


    private fun topShows() {
        binding.topShowsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topShowsAdapter = TopShowsAdapter(topShowsDataList)
        binding.topShowsRecycler.adapter = topShowsAdapter

    }

    private fun editorPicks() {
        binding.editorsPickRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        editorsPickAdapter = EditorsPickAdapter(editorsPickDataList)
        binding.editorsPickRecycler.adapter = editorsPickAdapter

    }

    private fun latestRecycler() {
        binding.latestRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        latestAdapter = LatestAdapter(latestDataList)
        binding.latestRecycler.adapter = latestAdapter

    }


    private fun observeLiveData() {
        viewModel.carouselData.observe(requireActivity(), {
            it.let {
                for (data in it.data) {
                    carouselImagesList.add(Carousel(data.feature_img, data.title))
                }
                slider()
            }
        })

        viewModel.latestData.observe(requireActivity(), {
            it.let {
                latestDataList.addAll(it.data)
                latestAdapter.notifyDataSetChanged()
                viewModel.isLoading.postValue(false)
            }
        })

        viewModel.editorsPickData.observe(requireActivity(), {
            it.let {
                editorsPickDataList.addAll(it.data)
                editorsPickAdapter.notifyDataSetChanged()
            }
        })

        viewModel.topShowsData.observe(requireActivity(), {
            it.let {
                topShowsDataList.addAll(it.data)
                topShowsAdapter.notifyDataSetChanged()
            }
        })
    }


    class ClickHandler {

        private lateinit var context: Context

        fun viewMore(view: View) {

            context = view.context

            when (view.id) {

                R.id.latestViewMore -> {
                    openViewMoreActivity(LATEST_URL)
                }

                R.id.editorsPickViewMore -> {
                    openViewMoreActivity(EDITORS_PICK_URL)
                }

                R.id.topShowsViewMore -> {
                    openViewMoreActivity(TOP_SHOWS_URL)
                }
            }
        }

        private fun openViewMoreActivity(url: String) {
            val intent = Intent(context, ViewMoreActivity::class.java)
            intent.putExtra("apiUrl", url)
            context.startActivity(intent)
        }

    }


    private fun slider() {

        imageSlider = binding.slider

        val requestOptions = RequestOptions().centerCrop()
        for (i in carouselImagesList.indices) {
            val sliderView = TextSliderView(context)

            sliderView.image(carouselImagesList[i].imageUrl)
                .description(carouselImagesList[i].title)
                .setRequestOption(requestOptions)
                .setProgressBarVisible(true)
                .setOnSliderClickListener(this)

            sliderView.bundle(Bundle())
            sliderView.bundle.putInt("position", i)
            sliderView.bundle.putString("extra", carouselImagesList[i].title)
            imageSlider.addSlider(sliderView)
        }
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom)
        imageSlider.setCustomAnimation(DescriptionAnimation())
        imageSlider.setDuration(3000)
        imageSlider.addOnPageChangeListener(this)
        imageSlider.stopCyclingWhenTouch(false)
    }


    override fun onSliderClick(slider: BaseSliderView?) {
        viewModel.carouselData.value.let {
            val intent = Intent(context, ShowDetailActivity::class.java)
            intent.putExtra(
                "topic_slug",
                it!!.data[slider!!.bundle.getInt("position")].show.topic_display.topic_slug
            )
            startActivity(intent)
        }

    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onDestroy() {
        imageSlider.stopAutoCycle()
        super.onDestroy()
    }

}