package com.infigo.alshamsazaan.di

import android.content.Context
import android.content.SharedPreferences
import com.infigo.alshamsazaan.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

}