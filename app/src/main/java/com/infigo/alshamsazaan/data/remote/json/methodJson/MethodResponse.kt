package com.infigo.alshamsazaan.data.remote.json.methodJson


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.base.BaseResponse
import com.infigo.alshamsazaan.data.remote.json.methodJson.MethodData

class MethodResponse : BaseResponse() {
    @SerializedName("data")
    lateinit var `data`: MethodData
}