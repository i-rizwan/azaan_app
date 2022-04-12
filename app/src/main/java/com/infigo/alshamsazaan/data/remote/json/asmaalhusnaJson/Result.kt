package com.infigo.alshamsazaan.data.remote.json.asmaalhusnaJson


import com.google.gson.annotations.SerializedName
import com.infigo.alshamsazaan.data.remote.json.asmaalhusnaJson.En

data class Result(
    @SerializedName("en")
    val en: En,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("transliteration")
    val transliteration: String
)