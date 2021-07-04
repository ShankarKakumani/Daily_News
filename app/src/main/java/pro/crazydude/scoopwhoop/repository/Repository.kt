package pro.crazydude.scoopwhoop.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pro.crazydude.scoopwhoop.model.CarouselModel
import pro.crazydude.scoopwhoop.model.LatestModel
import pro.crazydude.scoopwhoop.util.Constants.LATEST_URL
import pro.crazydude.scoopwhoop.util.Tools

class Repository(private val context: Context) {

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    fun getData(dataModel: MutableLiveData<CarouselModel>, apiUrl: String) {

        val carouselRequest = StringRequest(
            Request.Method.GET, apiUrl,
            { response ->
                val myResponse = Tools.getResponse(response, CarouselModel::class.java)
                dataModel.postValue(myResponse)
            },
            { error ->
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(latestRequest)
    }


}