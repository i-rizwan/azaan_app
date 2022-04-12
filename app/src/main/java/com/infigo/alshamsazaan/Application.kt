package com.infigo.alshamsazaan

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.work.Configuration
import com.infigo.alshamsazaan.data.local.SolatKuyRoom
import com.infigo.alshamsazaan.worker.MyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application(), Configuration.Provider {

    @Inject
    lateinit var db: SolatKuyRoom

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(MyWorkerFactory(db.notifiedPrayerDao(), db.msApi1Dao()))
            .build()
    }


}

