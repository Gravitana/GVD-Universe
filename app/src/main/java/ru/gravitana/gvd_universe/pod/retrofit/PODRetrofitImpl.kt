package ru.gravitana.gvd_universe.pod.retrofit

class PODRetrofitImpl {

    private val BASE_URL = "https://api.nasa.gov/"

    fun getRetrofitImpl() = RetrofitClient.getClient(BASE_URL)
}