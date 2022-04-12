package com.infigo.alshamsazaan.data.remote.json.methodJson


import com.google.gson.annotations.SerializedName

data class GULF(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)