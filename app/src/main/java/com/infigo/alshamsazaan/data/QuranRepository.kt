package com.infigo.alshamsazaan.data

import androidx.lifecycle.LiveData
import com.infigo.alshamsazaan.data.local.localentity.MsAyah
import com.infigo.alshamsazaan.data.local.localentity.MsFavSurah
import com.infigo.alshamsazaan.data.local.localentity.MsSurah
import com.infigo.alshamsazaan.data.remote.json.quranallsurahJson.AllSurahResponse
import com.infigo.alshamsazaan.data.remote.json.readsurahJsonAr.ReadSurahArResponse
import com.infigo.alshamsazaan.data.remote.json.readsurahJsonEn.ReadSurahEnResponse
import com.infigo.alshamsazaan.util.Resource
import kotlinx.coroutines.Deferred

interface QuranRepository {
    fun observeListFavSurah(): LiveData<List<MsFavSurah>>
    fun observeFavSurahBySurahID(surahID: Int): LiveData<MsFavSurah?>
    suspend fun insertFavSurah(msFavSurah: MsFavSurah)
    suspend fun deleteFavSurah(msFavSurah: MsFavSurah)
    suspend fun fetchReadSurahEn(surahID: Int): Deferred<ReadSurahEnResponse>
    suspend fun fetchAllSurah(): Deferred<AllSurahResponse>
    fun getAllSurah(): LiveData<Resource<List<MsSurah>>>
    suspend fun fetchReadSurahAr(surahID: Int): Deferred<ReadSurahArResponse>
    fun getAyahBySurahID(surahID: Int): LiveData<Resource<List<MsAyah>>>
}