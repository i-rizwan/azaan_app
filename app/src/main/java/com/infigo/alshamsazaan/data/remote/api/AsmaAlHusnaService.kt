package com.infigo.alshamsazaan.data.remote.api

import com.infigo.alshamsazaan.data.remote.json.asmaalhusnaJson.AsmaAlHusnaResponse
import retrofit2.Call
import retrofit2.http.GET

interface AsmaAlHusnaService {

    //http://http://api.aladhan.com/asmaAlHusna/

    @GET("asmaAlHusna/")
    suspend fun fetchAsmaAlHusnaApi(): Call<AsmaAlHusnaResponse>

}