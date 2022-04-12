package com.infigo.alshamsazaan.ui.dua

import android.util.Log
import com.infigo.alshamsazaan.R

import com.infigo.alshamsazaan.base.BaseActivity
import com.infigo.alshamsazaan.databinding.ActivityDuaBinding

import com.infigo.alshamsazaan.model.DuaExtraData
import java.lang.Exception
import java.lang.NullPointerException

class DuaActivity : BaseActivity<ActivityDuaBinding, DuaViewModel>(
    R.layout.activity_dua, DuaViewModel::class.java
) {

    companion object{
        const val DuaData = "dua_data"
    }

    private val TAG = "DuaActivity"

    override fun setListener(){
        try {
            if(intent == null) throw NullPointerException("DuaActivity intent")
            val duaData = intent.getParcelableExtra<DuaExtraData>(DuaData) ?: throw NullPointerException("DuaActivity getExtras")
            binding.tvPrayerTitle.text = duaData.duaTitle
            binding.tvPrayerAr.text = duaData.duaAr
            binding.tvPrayerLt.text = duaData.duaLt
            binding.tvPrayerEn.text = duaData.duaEn
            binding.tvPrayerIn.text = duaData.duaIn
            binding.tvPrayerRef.text = duaData.duaRef
        }
        catch (ex: Exception){
            Log.d(TAG, ex.message.toString())
            showBottomSheet(isCancelable = false, isFinish = true)
        }
    }

    override fun getViewBinding() = ActivityDuaBinding.inflate(layoutInflater)

}