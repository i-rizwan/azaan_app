package com.infigo.alshamsazaan.broadcaster

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.infigo.alshamsazaan.worker.FireAlarmManagerWorker
import com.infigo.alshamsazaan.worker.UpdateMonthAndYearWorker
import java.util.concurrent.TimeUnit

/*
 * Created by Katili Jiwo Adi Wiyono on 02/04/20.
 */

class BootCompleteReceiver: BroadcastReceiver() {

    private val TAG = "BootCompleteReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            try {
                if(context != null) {
                    fireAlarmManagerWorker(context)
                    fireUpdateMonthYearWorker(context)
                } else {
                    throw Exception()
                }
            }catch (ex: Exception){
                FirebaseCrashlytics.getInstance().setCustomKey("onReceive BootCompleteReceiver", ex.message.toString())
                Log.e(TAG, ex.message.toString())
                throw Exception(ex.message)
            }
        }
    }

    private fun fireAlarmManagerWorker(context: Context) {
        val task = PeriodicWorkRequest.Builder(FireAlarmManagerWorker::class.java, 60, TimeUnit.MINUTES)
            .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(FireAlarmManagerWorker.UNIQUE_KEY, ExistingPeriodicWorkPolicy.KEEP, task)
    }

    private fun fireUpdateMonthYearWorker(context: Context) {
        val task = PeriodicWorkRequest.Builder(UpdateMonthAndYearWorker::class.java, 720, TimeUnit.MINUTES)
            .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(UpdateMonthAndYearWorker.UNIQUE_KEY, ExistingPeriodicWorkPolicy.KEEP, task)
    }
}