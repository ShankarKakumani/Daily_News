package pro.crazydude.scoopwhoop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pro.crazydude.scoopwhoop.model.*
import pro.crazydude.scoopwhoop.repository.Repository
import pro.crazydude.scoopwhoop.util.Constants

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)
    val carouselData = MutableLiveData<CarouselModel>()
    val latestData = MutableLiveData<LatestModel>()
    val editorsPickData = MutableLiveData<EditorsPickModel>()
    val topShowsData = MutableLiveData<TopShowsModel>()

    fun loadCarousel() {
        repository.getData(carouselData, Constants.CAROUSEL_URL)
    }

    fun loadLatest() {
        repository.getLatestData(latestData)
    }

    fun loadEditorsPick() {
        repository.getEditorsPick(editorsPickData)
    }

    fun loadTopShows() {
        repository.getTopShows(topShowsData)
    }

}