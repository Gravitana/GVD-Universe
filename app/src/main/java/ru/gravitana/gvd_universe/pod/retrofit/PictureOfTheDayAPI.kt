package ru.gravitana.gvd_universe.pod.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gravitana.gvd_universe.pod.model.PODServerResponseData

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("date") date: String, @Query("api_key") apiKey: String): Call<PODServerResponseData>
}