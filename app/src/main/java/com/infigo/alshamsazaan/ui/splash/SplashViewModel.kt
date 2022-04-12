package com.infigo.alshamsazaan.ui.splash

import androidx.lifecycle.ViewModel
import com.infigo.alshamsazaan.data.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val prayerRepository: PrayerRepository): ViewModel() {
    val msSetting =  prayerRepository.observeMsSetting()
}