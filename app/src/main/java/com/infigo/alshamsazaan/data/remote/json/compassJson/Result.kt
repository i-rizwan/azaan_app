package com.infigo.alshamsazaan.data.remote.json.compassJson


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("direction")
    val direction: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)