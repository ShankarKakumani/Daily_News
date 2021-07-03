package pro.crazydude.scoopwhoop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pro.crazydude.scoopwhoop.model.DataModel
import pro.crazydude.scoopwhoop.repository.Repository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)
    val carouselData = MutableLiveData<DataModel>()

    fun loadCarousel() {
        repository.getCarousel(carouselData)
    }

}