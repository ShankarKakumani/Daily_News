package pro.crazydude.scoopwhoop.util

import com.google.gson.Gson

object Tools {

    fun <T> getResponse(response: String, model: Class<T>): T {
        return Gson().fromJson(response, model)
    }

}