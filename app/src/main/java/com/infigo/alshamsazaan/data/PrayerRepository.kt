package com.infigo.alshamsazaan.data

import androidx.lifecycle.LiveData
import com.infigo.alshamsazaan.data.local.localentity.MsApi1
import com.infigo.alshamsazaan.data.local.localentity.MsCalculationMethods
import com.infigo.alshamsazaan.data.local.localentity.MsSetting
import com.infigo.alshamsazaan.data.local.localentity.MsNotifiedPrayer
import com.infigo.alshamsazaan.data.remote.json.compassJson.CompassResponse
import com.infigo.alshamsazaan.data.remote.json.prayerJson.PrayerResponse
import com.infigo.alshamsazaan.util.Resource
import kotlinx.coroutines.Deferred

interface PrayerRepository {
    suspend fun updatePrayerIsNotified(prayerName: String, isNotified: Boolean)
    suspend fun updatePrayerTime(prayerName: String, prayerTime: String)
    fun observeMsApi1(): LiveData<MsApi1>
    suspend fun updateMsApi1(msApi1: MsApi1)
    suspend fun updateMsApi1Method(api1ID: Int, methodID: String)
    fun observeMsSetting(): LiveData<MsSetting>
    suspend fun updateMsApi1MonthAndYear(api1ID: Int, month: String, year:String)
    suspend fun updateIsHasOpenApp(isHasOpen: Boolean)
    suspend fun getListNotifiedPrayer(): List<MsNotifiedPrayer>
    suspend fun fetchQibla(msApi1: MsApi1): Deferred<CompassResponse>
    suspend fun fetchPrayerApi(msApi1: MsApi1): Deferred<PrayerResponse>
    fun getListNotifiedPrayer(msApi1: MsApi1): LiveData<Resource<List<MsNotifiedPrayer>>>
    fun getMethods(): LiveData<Resource<List<MsCalculationMethods>>>
}