package com.infigo.alshamsazaan.data.remote.json.prayerJson


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse

class PrayerResponse: BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: List<Result>
}