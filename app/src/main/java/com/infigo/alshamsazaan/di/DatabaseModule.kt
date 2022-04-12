package com.infigo.alshamsazaan.di

import android.content.Context
import androidx.room.Room
import com.infigo.alshamsazaan.data.local.SolatKuyRoom
import com.infigo.alshamsazaan.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSolatKuyDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, SolatKuyRoom::class.java, Constant.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    /* DAO */
    @Singleton
    @Provides
    fun provideNotifiedPrayerDao(db: SolatKuyRoom) = db.notifiedPrayerDao()

    @Singleton
    @Provides
    fun provideMsApi1Dao(db: SolatKuyRoom) = db.msApi1Dao()

    @Singleton
    @Provides
    fun provideMsSettingDao(db: SolatKuyRoom) = db.msSettingDao()

    @Singleton
    @Provides
    fun provideMsFavSurahDao(db: SolatKuyRoom) = db.msFavSurahDao()

    @Singleton
    @Provides
    fun provideMsSurahDao(db: SolatKuyRoom) = db.msSurahDao()

    @Singleton
    @Provides
    fun provideMsAyahDao(db: SolatKuyRoom) = db.msAyahDao()

    @Singleton
    @Provides
    fun provideMsCalculationMethodsDao(db: SolatKuyRoom) = db.msCalculationMethodsDao()
}