package com.infigo.alshamsazaan.data.remote.api

import com.infigo.alshamsazaan.data.remote.json.quranallsurahJson.AllSurahResponse
import retrofit2.Call
import retrofit2.http.GET

interface AllSurahService {

    //http://api.alquran.cloud/v1/juz/30/en.asad

    @GET("surah")
    fun fetchAllSurah(): Call<AllSurahResponse>
}