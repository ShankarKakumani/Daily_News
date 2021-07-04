package pro.crazydude.scoopwhoop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pro.crazydude.scoopwhoop.model.ShowDetailModel
import pro.crazydude.scoopwhoop.repository.Repository

class ShowDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)
    val showDetailsData = MutableLiveData<ShowDetailModel>()


    fun loadShowDetail(topic_slug: String) {
        repository.getShowDetail(showDetailsData, topic_slug)
    }

}