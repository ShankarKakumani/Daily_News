package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.animations.DescriptionAnimation
import com.glide.slider.library.slidertypes.BaseSliderView
import com.glide.slider.library.slidertypes.BaseSliderView.OnSliderClickListener
import com.glide.slider.library.slidertypes.TextSliderView
import com.glide.slider.library.tricks.ViewPagerEx
import pro.crazydude.scoopwhoop.adapter.EditorsPickAdapter
import pro.crazydude.scoopwhoop.adapter.LatestAdapter
import pro.crazydude.scoopwhoop.databinding.ActivityMainBinding
import pro.crazydude.scoopwhoop.model.Carousel
import pro.crazydude.scoopwhoop.model.Data
import pro.crazydude.scoopwhoop.model.EditorsPickData
import pro.crazydude.scoopwhoop.model.LatestData
import pro.crazydude.scoopwhoop.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity(), OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var imageSlider: SliderLayout

    private val carouselImagesList: ArrayList<Carousel> = ArrayList()
    private val latestDataList: ArrayList<LatestData> = ArrayList()
    private val editorsPickDataList: ArrayList<EditorsPickData> = ArrayList()

    private lateinit var latestAdapter: LatestAdapter
    private lateinit var editorsPickAdapter: EditorsPickAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        initViews()
        bindData()
        observeLiveData()
    }

    private fun initViews() {
        latestRecycler()
        loadEditorsPicks()
    }

    private fun loadEditorsPicks() {
        binding.editorsPickRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        editorsPickAdapter = EditorsPickAdapter(editorsPickDataList)
        binding.editorsPickRecycler.adapter = editorsPickAdapter

    }

    private fun latestRecycler() {
        binding.latestRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        latestAdapter = LatestAdapter(latestDataList)
        binding.latestRecycler.adapter = latestAdapter

    }


    private fun observeLiveData() {
        viewModel.carouselData.observe(this, {
            it.let {
                for (data in it.data) {
                    carouselImagesList.add(Carousel(data.feature_img, data.title))
                }
                slider()
            }
        })

        viewModel.latestData.observe(this, {
            it.let {
                latestDataList.addAll(it.data)
                latestAdapter.notifyDataSetChanged()
            }
        })

        viewModel.editorsPickData.observe(this, {
            it.let {
                editorsPickDataList.addAll(it.data)
                editorsPickAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun bindData() {
        viewModel.loadCarousel()
        viewModel.loadLatest()
        viewModel.loadEditorsPick()
    }

    private fun slider() {
        imageSlider = binding.slider

        val requestOptions = RequestOptions()
        requestOptions.centerCrop()
        for (i in carouselImagesList.indices) {
            val sliderView = TextSliderView(this)

            sliderView.image(carouselImagesList[i].imageUrl)
                .description(carouselImagesList[i].title)
                .setRequestOption(requestOptions)
                .setProgressBarVisible(true)
                .setOnSliderClickListener(this)

            sliderView.bundle(Bundle())
            sliderView.bundle.putString("extra", carouselImagesList[i].title);
            imageSlider.addSlider(sliderView)
        }
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom)
        imageSlider.setCustomAnimation(DescriptionAnimation())
        imageSlider.setDuration(3000)
        imageSlider.addOnPageChangeListener(this)
        imageSlider.stopCyclingWhenTouch(false)
    }


    override fun onSliderClick(slider: BaseSliderView?) {

    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onDestroy() {
        imageSlider.stopAutoCycle()
        super.onDestroy()
    }

}