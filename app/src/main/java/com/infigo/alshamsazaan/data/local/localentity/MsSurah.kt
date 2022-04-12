package com.infigo.alshamsazaan.data.local.localentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ms_surah")
data class MsSurah(
    @PrimaryKey
    val number: Int,
    val englishName: String,
    var englishNameLowerCase: String?,
    val englishNameTranslation: String,
    val name: String,
    val numberOfAyahs: Int,
    val revelationType: String
)