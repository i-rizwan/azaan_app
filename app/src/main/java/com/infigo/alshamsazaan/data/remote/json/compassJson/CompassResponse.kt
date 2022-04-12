package com.infigo.alshamsazaan.data.remote.json.compassJson


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse

class CompassResponse: BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: Result
}