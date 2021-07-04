package pro.crazydude.scoopwhoop.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pro.crazydude.scoopwhoop.model.*
import pro.crazydude.scoopwhoop.util.Constants.CAROUSEL_URL
import pro.crazydude.scoopwhoop.util.Constants.EDITORS_PICK_URL
import pro.crazydude.scoopwhoop.util.Constants.LATEST_URL
import pro.crazydude.scoopwhoop.util.Constants.SHOW_DETAILS_URL
import pro.crazydude.scoopwhoop.util.Constants.TOP_SHOWS_URL
import pro.crazydude.scoopwhoop.util.Tools

class Repository(private val context: Context) {

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    fun getData(dataModel: MutableLiveData<CarouselModel>, haveInternet: MutableLiveData<Boolean>) {

        val carouselRequest = StringRequest(
            Request.Method.GET, CAROUSEL_URL,
            { response ->
                val myResponse = Tools.getResponse(response, CarouselModel::class.java)
                dataModel.postValue(myResponse)
                haveInternet.postValue(true)
            },
            { error ->
                when(error) {
                    is NetworkError ->
                    {
                        haveInternet.postValue(false)
                        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        error.printStackTrace()
                    }

                }
            }
        )

        queue.add(carouselRequest)
    }

    fun getLatestData(dataModel: MutableLiveData<LatestModel>) {

        val latestRequest = StringRequest(
            Request.Method.GET, LATEST_URL,
            { response ->
                val myResponse = Tools.getResponse(response, LatestModel::class.java)
                dataModel.postValue(myResponse)
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(latestRequest)
    }

    fun getEditorsPick(dataModel: MutableLiveData<EditorsPickModel>) {

        val latestRequest = StringRequest(
            Request.Method.GET, EDITORS_PICK_URL,
            { response ->
                val myResponse = Tools.getResponse(response, EditorsPickModel::class.java)
                dataModel.postValue(myResponse)
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(latestRequest)
    }



    fun getTopShows(dataModel: MutableLiveData<TopShowsModel>) {

        val topShowsRequest = StringRequest(
            Request.Method.GET, TOP_SHOWS_URL,
            { response ->
                val myResponse = Tools.getResponse(response, TopShowsModel::class.java)
                dataModel.postValue(myResponse)
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(topShowsRequest)
    }

    fun getShowDetail(
        dataModel: MutableLiveData<ShowDetailModel>,
        topicSlug: String,
        offset: Int,
        haveInternet: MutableLiveData<Boolean>
    ) {
        val url = if(offset == -1 || offset == 0) {
            "$SHOW_DETAILS_URL$topicSlug"
        } else {
            "$SHOW_DETAILS_URL$topicSlug&offset=$offset"
        }

        val showDetailRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val myResponse = Tools.getResponse(response, ShowDetailModel::class.java)
                dataModel.postValue(myResponse)
                haveInternet.postValue(true)
            },
            { error ->

                when(error) {
                    is NetworkError ->
                    {
                        haveInternet.postValue(false)
                        Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        )
        queue.add(showDetailRequest)
    }

}