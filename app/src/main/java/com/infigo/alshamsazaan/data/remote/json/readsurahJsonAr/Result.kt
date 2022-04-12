package com.infigo.alshamsazaan.data.remote.json.readsurahJsonAr


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.data.remote.json.readsurahJsonAr.Ayah
import com.infigo.alshamsazaan.data.remote.json.readsurahJsonAr.Edition

data class Result(
    @SerializedName("ayahs")
    var ayahs: List<Ayah>,
    @SerializedName("edition")
    val edition: Edition,
    @SerializedName("englishName")
    var englishName: String,
    @SerializedName("englishNameTranslation")
    val englishNameTranslation: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfAyahs")
    val numberOfAyahs: Int,
    @SerializedName("revelationType")
    val revelationType: String
)