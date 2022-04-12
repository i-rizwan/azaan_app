package com.infigo.alshamsazaan.data.remote.json.readsurahJsonAr


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse

class ReadSurahArResponse: BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: Result
}