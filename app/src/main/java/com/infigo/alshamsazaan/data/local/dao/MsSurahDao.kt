package com.infigo.alshamsazaan.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infigo.alshamsazaan.data.local.localentity.MsSurah

@Dao
interface MsSurahDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSurahs(msSurah: List<MsSurah>)

    @Query("delete from ms_surah")
    fun deleteSurahs()

    @Query("select * from ms_surah")
    fun getSurahs(): LiveData<List<MsSurah>>
}