package com.infigo.alshamsazaan.data.remote.json.readsurahJsonEn


import com.google.gson.annotations.SerializedName

data class Ayah(
    @SerializedName("hizbQuarter")
    val hizbQuarter: Int,
    @SerializedName("juz")
    val juz: Int,
    @SerializedName("manzil")
    val manzil: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberInSurah")
    val numberInSurah: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("ruku")
    val ruku: Int,
    @SerializedName("text")
    val text: String
)