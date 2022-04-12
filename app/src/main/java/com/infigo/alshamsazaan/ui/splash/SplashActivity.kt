package com.infigo.alshamsazaan.ui.splash

import android.os.Handler
import android.os.Looper
import com.infigo.alshamsazaan.R

import com.infigo.alshamsazaan.base.BaseActivity
import com.infigo.alshamsazaan.data.local.SolatKuyRoom
import com.infigo.alshamsazaan.databinding.ActivitySplashBinding

import com.infigo.alshamsazaan.ui.boarding.BoardingActivity
import com.infigo.alshamsazaan.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(
    R.layout.activity_splash,
    SplashViewModel::class.java
) {

    private val splashDelay : Long = 1000

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun setListener() {
        super.setListener()
        observeDb()
    }

    private fun observeDb(){
        viewModel.msSetting.observe(this, {
            if(it != null){
                if(it.isHasOpenApp){
                    gotoMainActivity()
                } else {
                    gotoBoardingActivity()
                }
            } else {
                SolatKuyRoom.populateDatabase(db)
            }
        })
    }

    private fun gotoMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            gotoIntent(MainActivity::class.java, null, true)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, splashDelay)
    }

    private fun gotoBoardingActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            gotoIntent(BoardingActivity::class.java, null, true)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, splashDelay)
    }
}