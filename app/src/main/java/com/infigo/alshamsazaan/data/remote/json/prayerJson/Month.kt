package com.infigo.alshamsazaan.data.remote.json.prayerJson


import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("en")
    var en: String,
    @SerializedName("number")
    val number: Int
)