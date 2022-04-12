package com.infigo.alshamsazaan.data.remote.json.quranallsurahJson


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse

class AllSurahResponse: BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: List<Result>
}