package pro.crazydude.scoopwhoop.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import pro.crazydude.scoopwhoop.model.DataModel
import pro.crazydude.scoopwhoop.util.Constants.CAROUSEL_URL
import pro.crazydude.scoopwhoop.util.Tools

class Repository(private val context: Context) {

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    fun getCarousel(carouselData: MutableLiveData<DataModel>) {

        val carouselRequest = StringRequest(
            Request.Method.GET, CAROUSEL_URL,
            { response ->

                val myResponse = Tools.getResponse(response, DataModel::class.java)
                carouselData.postValue(myResponse)

            },
            { error ->
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(carouselRequest)
    }



}