package com.infigo.alshamsazaan.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.infigo.alshamsazaan.data.PrayerRepositoryImpl
import com.infigo.alshamsazaan.data.QuranRepositoryImpl
import com.infigo.alshamsazaan.ui.main.qibla.CompassFragment
import com.infigo.alshamsazaan.ui.main.qibla.CompassViewModel
import com.infigo.alshamsazaan.ui.main.home.HomeFragment
import com.infigo.alshamsazaan.ui.main.home.HomeViewModel
import com.infigo.alshamsazaan.ui.main.quran.listsurah.ListSurahFragment
import com.infigo.alshamsazaan.ui.main.quran.listsurah.ListSurahViewModel
import com.infigo.alshamsazaan.ui.main.setting.SettingViewModel
import com.infigo.alshamsazaan.ui.main.setting.SettingFragment
import javax.inject.Inject

class SolatKuyFragmentFactory @Inject constructor(
    private val prayerRepositoryImpl: PrayerRepositoryImpl,
    private val quranRepositoryImpl: QuranRepositoryImpl
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            CompassFragment::class.java.name -> CompassFragment(
                CompassViewModel(prayerRepositoryImpl)
            )
            ListSurahFragment::class.java.name -> ListSurahFragment(
                ListSurahViewModel(quranRepositoryImpl)
            )
            SettingFragment::class.java.name -> SettingFragment(
                SettingViewModel(prayerRepositoryImpl)
            )
            HomeFragment::class.java.name -> HomeFragment(
                HomeViewModel(prayerRepositoryImpl,quranRepositoryImpl)
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}