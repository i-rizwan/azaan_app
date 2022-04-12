package com.infigo.alshamsazaan.ui.main.qibla

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infigo.alshamsazaan.data.PrayerRepository
import com.infigo.alshamsazaan.data.local.localentity.MsApi1
import com.infigo.alshamsazaan.data.remote.json.compassJson.CompassResponse
import com.infigo.alshamsazaan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor(val prayerRepository: PrayerRepository): ViewModel() {

    val msApi1 = prayerRepository.observeMsApi1()

    private var _compass = MutableLiveData<Resource<CompassResponse>>()
    val compass: LiveData<Resource<CompassResponse>> = _compass
    fun fetchCompassApi(msApi1: MsApi1){
        viewModelScope.launch {
            _compass.postValue(Resource.loading(null))
            try {
                val response = prayerRepository.fetchQibla(msApi1).await()
                if(response.responseStatus == "1"){
                    _compass.postValue(Resource.success(response))
                } else {
                    _compass.postValue(Resource.error(null, response.message))
                }
            }
            catch (ex: Exception){
                _compass.postValue(Resource.error(null, ex.message.toString()))
            }
        }
    }

}