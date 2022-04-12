package com.infigo.alshamsazaan.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.infigo.alshamsazaan.R

import com.infigo.alshamsazaan.data.local.SolatKuyRoom
import com.infigo.alshamsazaan.databinding.LayoutErrorBottomsheetBinding
import com.infigo.alshamsazaan.util.SharedPrefUtil
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val layout: Int,
    private val viewModelClass: Class<VM>?,
) : AppCompatActivity() {

    @Inject
    lateinit var db: SolatKuyRoom

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil

    protected val LOCATION_PERMISSIONS = 101
    protected lateinit var viewModel: VM

    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        viewModelClass?.let {
            viewModel = ViewModelProvider(this).get(it)
        }
        inflateBinding()
        setListener()
        setContentView(requireNotNull(_binding).root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    protected open fun setListener() {}

    protected open fun inflateBinding() {}

    protected fun isLocationPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    protected fun listLocationPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    protected fun <T : Any> gotoIntent(classIntent: Class<T>, bundle: Bundle?, isFinish: Boolean) {
        val intent = Intent(this, classIntent)
        if (bundle != null)
            intent.putExtras(bundle)
        startActivity(intent)
        if (isFinish)
            finish()
    }

    protected fun showBottomSheet(
        title: String = resources.getString(R.string.text_error_title),
        description: String = resources.getString(R.string.text_error_dsc),
        isCancelable: Boolean = true,
        isFinish: Boolean = false,
        callback: (() -> Unit)? = null
    ) {

        val dialog = BottomSheetDialog(this)
        val dialogBinding = LayoutErrorBottomsheetBinding.inflate(layoutInflater)
        dialog.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(isCancelable)
            setContentView(dialogBinding.root)
        }
        dialogBinding.apply {
            tvTitle.text = title
            tvDesc.text = description
        }
        dialog.show()
        dialogBinding.btnOk.setOnClickListener {
            dialog.hide()
            callback?.invoke()
            if (isFinish)
                finish()
        }
    }
}