package com.infigo.alshamsazaan.ui.boarding

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.infigo.alshamsazaan.R

import com.infigo.alshamsazaan.base.BaseActivity
import com.infigo.alshamsazaan.data.local.localentity.MsApi1
import com.infigo.alshamsazaan.databinding.ActivityBoardingBinding
import com.infigo.alshamsazaan.databinding.LayoutBottomsheetBygpsBinding
import com.infigo.alshamsazaan.databinding.LayoutBottomsheetBylatitudelongitudeBinding

import com.infigo.alshamsazaan.ui.main.MainActivity
import com.infigo.alshamsazaan.util.Constant
import com.infigo.alshamsazaan.util.EnumStatus
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

import org.joda.time.LocalDate

@AndroidEntryPoint
class BoardingActivity : BaseActivity<ActivityBoardingBinding, BoardingViewModel>(
    R.layout.activity_boarding,
    BoardingViewModel::class.java
), View.OnClickListener {

    private lateinit var bsByGpsBinding: LayoutBottomsheetBygpsBinding
    private lateinit var bsByLatLngBinding: LayoutBottomsheetBylatitudelongitudeBinding
    private lateinit var bottomSheetDialog: Dialog
    private var isHasOpenSettingButton = false

    override fun getViewBinding(): ActivityBoardingBinding =
        ActivityBoardingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(this)
    }

    override fun onStart() {
        super.onStart()
        if (isHasOpenSettingButton) {
            getGPSLocation()
            isHasOpenSettingButton = false
        }
    }

    override fun setListener() {
        super.setListener()

        binding.btnByLatitudeLongitude.setOnClickListener(this)
        binding.btnByGps.setOnClickListener(this)
        bsByGpsBinding.btnProceedByGps.setOnClickListener(this)
        bsByLatLngBinding.btnProceedByLL.setOnClickListener(this)

        viewModel.msSetting.observe(this, {
            if (it != null && it.isHasOpenApp) {
                gotoIntent(MainActivity::class.java, null, true)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        })
    }

    override fun inflateBinding() {
        bsByLatLngBinding = LayoutBottomsheetBylatitudelongitudeBinding.inflate(layoutInflater)
        bsByGpsBinding = LayoutBottomsheetBygpsBinding.inflate(layoutInflater)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_by_latitude_longitude -> {
                bottomSheetDialog.setContentView(bsByLatLngBinding.root)
                bottomSheetDialog.show()
            }
            R.id.btn_proceedByLL -> {
                val latitude = bsByLatLngBinding.etLlDialogLatitude.text.toString().trim()
                val longitude = bsByLatLngBinding.etLlDialogLongitude.text.toString().trim()
                insertLocationSettingToDb(latitude, longitude)
            }
            R.id.btn_by_gps -> {
                bottomSheetDialog.setContentView(bsByGpsBinding.root)
                bottomSheetDialog.show()
                getGPSLocation()
            }
            R.id.btn_proceedByGps -> {
                if (bsByGpsBinding.tvGpsDialogLatitude.visibility != View.VISIBLE &&
                    bsByGpsBinding.tvViewLongitude.visibility != View.VISIBLE
                ) {
                    isHasOpenSettingButton = true
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                } else {
                    val latitude = bsByGpsBinding.tvGpsDialogLatitude.text.toString().trim()
                    val longitude = bsByGpsBinding.tvGpsDialogLongitude.text.toString().trim()
                    insertLocationSettingToDb(latitude, longitude)
                }
            }
        }
    }

    private fun updateIsHasOpenApp() {
        bottomSheetDialog.dismiss()
        viewModel.updateIsHasOpenApp(true)
    }

    private fun insertLocationSettingToDb(latitude: String, longitude: String) {
        val latitudeAndLongitudeCannotBeEmpty = "latitude and longitude cannot be empty"
        val latitudeAndLongitudeCannotBeEndedWithDot =
            "latitude and longitude cannot be ended with ."
        val latitudeAndLongitudeCannotBeStartedWithDot =
            "latitude and longitude cannot be started with ."
        val successChangeTheCoordinate = "Success change the coordinate"

        val msApi1 = MsApi1(
            1,
            latitude,
            longitude,
            Constant.STARTED_METHOD,
            LocalDate().monthOfYear.toString(),
            LocalDate().year.toString()
        )

        if (msApi1.latitude.isEmpty() || msApi1.longitude.isEmpty() || msApi1.latitude == "." || msApi1.longitude == ".") {
            Toasty.error(this, latitudeAndLongitudeCannotBeEmpty, Toasty.LENGTH_SHORT).show()
            return
        }

        val arrLatitude = msApi1.latitude.toCharArray()
        val arrLongitude = msApi1.longitude.toCharArray()

        if (arrLatitude[arrLatitude.size - 1] == '.' || arrLongitude[arrLongitude.size - 1] == '.') {
            Toasty.error(this, latitudeAndLongitudeCannotBeEndedWithDot, Toasty.LENGTH_SHORT).show()
            return
        }

        if (arrLatitude[0] == '.' || arrLongitude[0] == '.') {
            Toasty.error(this, latitudeAndLongitudeCannotBeStartedWithDot, Toasty.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.updateMsApi1(msApi1)
        Toasty.success(this, successChangeTheCoordinate, Toasty.LENGTH_SHORT).show()

        updateIsHasOpenApp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onUpdateLocationListener()
            } else {
                bottomSheetDialog.dismiss()
                Toasty.error(
                    this,
                    getString(R.string.permission_is_needed_to_run_the_gps),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.location_needed))
            .setMessage(getString(R.string.permission_is_needed_to_run_the_gps))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.oke)) { _: DialogInterface, _: Int ->
                ActivityCompat.requestPermissions(
                    this,
                    listLocationPermission(),
                    LOCATION_PERMISSIONS
                )
            }
            .setNegativeButton(getString(R.string.cancel)) { _: DialogInterface, _: Int ->
                bottomSheetDialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getGPSLocation() {
        if (isLocationPermissionGranted()) {
            setGpsBottomSheetState()
            onUpdateLocationListener()
        } else {
            showPermissionDialog()
            return
        }
    }

    private fun setGpsBottomSheetState() {
        val lm: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: java.lang.Exception) {
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: java.lang.Exception) {
        }
        if (!gpsEnabled && !networkEnabled)
            setGpsComponentState(EnumStatus.ERROR)
        else
            setGpsComponentState(EnumStatus.LOADING)
    }

    @SuppressLint("MissingPermission")
    private fun onUpdateLocationListener() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 10 * 1000 /* 10 minute */
        mLocationRequest.fastestInterval = 1 * 1000 /* 1 second */
        if (!isLocationPermissionGranted()) {
            showPermissionDialog()
            return
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(
                mLocationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val location = locationResult.lastLocation ?: return
                        setGpsComponentState(EnumStatus.SUCCESS)
                        bsByGpsBinding.tvGpsDialogLatitude.text = location.latitude.toString()
                        bsByGpsBinding.tvGpsDialogLongitude.text = location.longitude.toString()
                    }
                },
                Looper.myLooper()!!
            )
    }

    private fun setGpsComponentState(status: EnumStatus) {
        when (status) {
            EnumStatus.SUCCESS -> {
                bsByGpsBinding.tvViewLatitude.visibility = View.VISIBLE
                bsByGpsBinding.tvViewLongitude.visibility = View.VISIBLE
                bsByGpsBinding.tvGpsDialogLongitude.visibility = View.VISIBLE
                bsByGpsBinding.tvGpsDialogLatitude.visibility = View.VISIBLE
                bsByGpsBinding.ivWarning.visibility = View.INVISIBLE
                bsByGpsBinding.tvWarning.visibility = View.INVISIBLE
                bsByGpsBinding.btnProceedByGps.text = getString(R.string.proceed)
                bsByGpsBinding.btnProceedByGps.visibility = View.VISIBLE
                bsByGpsBinding.btnProceedByGps.text = getString(R.string.proceed)
            }
            EnumStatus.LOADING -> {
                bsByGpsBinding.ivWarning.visibility = View.VISIBLE
                bsByGpsBinding.tvWarning.visibility = View.VISIBLE
                bsByGpsBinding.tvWarning.text = getString(R.string.loading)
                bsByGpsBinding.tvViewLatitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvViewLongitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvGpsDialogLongitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvGpsDialogLatitude.visibility = View.INVISIBLE
                bsByGpsBinding.btnProceedByGps.visibility = View.INVISIBLE
            }
            EnumStatus.ERROR -> {
                bsByGpsBinding.ivWarning.visibility = View.VISIBLE
                bsByGpsBinding.tvWarning.visibility = View.VISIBLE
                bsByGpsBinding.tvWarning.text = getString(R.string.please_enable_your_location)
                bsByGpsBinding.tvGpsDialogLongitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvGpsDialogLatitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvViewLatitude.visibility = View.INVISIBLE
                bsByGpsBinding.tvViewLongitude.visibility = View.INVISIBLE
                bsByGpsBinding.btnProceedByGps.text = getString(R.string.open_setting)
                bsByGpsBinding.btnProceedByGps.visibility = View.VISIBLE
                bsByGpsBinding.btnProceedByGps.text = getString(R.string.open_setting)
            }
        }
    }
}