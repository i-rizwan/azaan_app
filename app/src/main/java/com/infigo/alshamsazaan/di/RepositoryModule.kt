package com.infigo.alshamsazaan.di

import com.infigo.alshamsazaan.data.local.dao.*
import com.infigo.alshamsazaan.data.remote.api.*
import com.infigo.alshamsazaan.data.PrayerRepository
import com.infigo.alshamsazaan.data.PrayerRepositoryImpl
import com.infigo.alshamsazaan.data.QuranRepository
import com.infigo.alshamsazaan.data.QuranRepositoryImpl
import com.infigo.alshamsazaan.util.ContextProviders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideContextProvider(): ContextProviders = ContextProviders.getInstance()

    @Singleton
    @Provides
    fun providePrayerRepositoryImpl(
        msNotifiedPrayerDao: MsNotifiedPrayerDao,
        msApi1Dao: MsApi1Dao,
        msSettingDao: MsSettingDao,
        msCalculationMethodsDao: MsCalculationMethodsDao,
        contextProviders: ContextProviders,
        qiblaApiService: QiblaApiService,
        prayerApiService: PrayerApiService
    ) = PrayerRepositoryImpl(msNotifiedPrayerDao,
        msApi1Dao, msSettingDao, msCalculationMethodsDao, contextProviders, qiblaApiService, prayerApiService) as PrayerRepository

    @Singleton
    @Provides
    fun provideQuranRepositoryImpl(
        msFavSurahDao: MsFavSurahDao,
        msSurahDao: MsSurahDao,
        msAyahDao: MsAyahDao,
        readSurahEnService: ReadSurahEnService,
        allSurahService: AllSurahService,
        readSurahArService: ReadSurahArService,
        contextProviders: ContextProviders
    ) = QuranRepositoryImpl(
        msFavSurahDao,
        msSurahDao,
        msAyahDao,
        readSurahEnService,
        allSurahService,
        readSurahArService,
        contextProviders,
    ) as QuranRepository


}