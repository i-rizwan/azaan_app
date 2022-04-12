package com.infigo.alshamsazaan.ui.main.quran.listsurah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.infigo.alshamsazaan.data.QuranRepository
import com.infigo.alshamsazaan.data.local.localentity.MsSurah
import com.infigo.alshamsazaan.util.Resource
import com.infigo.alshamsazaan.util.livedata.AbsentLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListSurahViewModel @Inject constructor(private val quranRepository: QuranRepository): ViewModel() {

    val staredSurah = quranRepository.observeListFavSurah()

    private var isAyahsCalled = MutableLiveData(false)
    val allSurah: LiveData<Resource<List<MsSurah>>> = Transformations.switchMap(isAyahsCalled){ isAyahsCalled ->
        if (!isAyahsCalled) {
            AbsentLiveData.create()
        } else {
            quranRepository.getAllSurah()
        }
    }
    fun getAllSurah(value: Boolean = true){
        this.isAyahsCalled.value = value
    }

}