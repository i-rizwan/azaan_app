package com.infigo.alshamsazaan.data.remote.json.prayerJson


import com.google.gson.annotations.SerializedName

data class WeekdayX(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("en")
    val en: String
)