package com.infigo.alshamsazaan.data.remote.json.readsurahJsonEn


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse

class ReadSurahEnResponse: BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: Result
}