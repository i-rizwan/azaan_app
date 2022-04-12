package com.infigo.alshamsazaan.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infigo.alshamsazaan.data.local.localentity.MsAyah

@Dao
interface MsAyahDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAyahs(msAyah: List<MsAyah>)

    @Query("DELETE FROM ms_ayah WHERE surahID = :surahID")
    fun deleteAyahsBySurahID(surahID: Int)

    @Query("SELECT * FROM ms_ayah WHERE surahID = :surahID")
    fun getAyahsBySurahID(surahID: Int): LiveData<List<MsAyah>>
}