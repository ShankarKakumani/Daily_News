package pro.crazydude.scoopwhoop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pro.crazydude.scoopwhoop.model.ShowDetailModel
import pro.crazydude.scoopwhoop.repository.Repository
import kotlin.math.absoluteValue

class ShowDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: Repository = Repository(application)
    val showDetailsData = MutableLiveData<ShowDetailModel>()
    val offset = MutableLiveData(0)
    private val currentOffset = MutableLiveData(0)
    val topicSlug = MutableLiveData("")
    val isLoading = MutableLiveData(true)
    val loadMore = MutableLiveData(false)
    val haveInternet = MutableLiveData(true)

    fun loadShowDetail() {
        if(offset.value!!.toInt() == 0 || offset.value!!.toInt() > currentOffset.value!!.toInt()) {
            currentOffset.postValue(offset.value)
            repository.getShowDetail(showDetailsData,
                topicSlug.value.toString(),
                offset.value!!.absoluteValue,
                haveInternet
            )
        }
    }

}