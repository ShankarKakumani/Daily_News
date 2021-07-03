package pro.crazydude.scoopwhoop.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.animations.DescriptionAnimation
import com.glide.slider.library.slidertypes.BaseSliderView
import com.glide.slider.library.slidertypes.BaseSliderView.OnSliderClickListener
import com.glide.slider.library.slidertypes.DefaultSliderView
import com.glide.slider.library.slidertypes.TextSliderView
import com.glide.slider.library.tricks.ViewPagerEx
import pro.crazydude.scoopwhoop.databinding.ActivityMainBinding
import pro.crazydude.scoopwhoop.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() , OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var imageSlider: SliderLayout

    private val carouselImagesList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

//        initViews()
        bindData()
        observeLiveData()
    }


    private fun observeLiveData() {
        viewModel.carouselData.observe(this, {
            it.let {
                for(data in it.data)
                {
                    carouselImagesList.add(data.feature_img)
                }
                slider()
            }
        })
    }

    private fun bindData() {
        viewModel.loadCarousel()
    }

    private fun slider() {
        imageSlider = binding.slider

        val requestOptions = RequestOptions()
        requestOptions.centerCrop()
        for (i in carouselImagesList.indices) {
            val sliderView = TextSliderView(this)

            sliderView.image(carouselImagesList[i])
                .description("Description")
                .setRequestOption(requestOptions)
                .setProgressBarVisible(true)
                .setOnSliderClickListener(this)

            sliderView.bundle(Bundle())
            sliderView.bundle.putString("extra", "Description");
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