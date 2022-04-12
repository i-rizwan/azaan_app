package com.infigo.alshamsazaan.ui.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infigo.alshamsazaan.data.PrayerRepository
import com.infigo.alshamsazaan.data.local.localentity.MsApi1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(val prayerRepository: PrayerRepository) : ViewModel(){

    val msSetting =  prayerRepository.observeMsSetting()

    fun updateMsApi1(msApi1: MsApi1) = viewModelScope.launch {
        prayerRepository.updateMsApi1(msApi1)
    }

    fun updateIsHasOpenApp(isHasOpen: Boolean) = viewModelScope.launch{
        prayerRepository.updateIsHasOpenApp(isHasOpen)
    }

}