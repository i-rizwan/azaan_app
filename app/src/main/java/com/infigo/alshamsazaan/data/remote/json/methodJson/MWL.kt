package com.infigo.alshamsazaan.data.remote.json.methodJson


import com.google.gson.annotations.SerializedName

data class MWL(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)