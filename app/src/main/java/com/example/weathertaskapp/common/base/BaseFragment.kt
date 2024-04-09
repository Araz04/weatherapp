package com.example.weathertaskapp.common.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.weathertaskapp.common.extension.setupAppErrorDialog
import com.example.weathertaskapp.common.extension.setupAppStringSRCErrorDialog
import com.example.weathertaskapp.common.extension.setupBackHandler
import com.example.weathertaskapp.common.extension.setupProgressDialog

abstract class BaseFragment : Fragment(), DefaultLifecycleObserver {

    abstract fun getViewModel(): BaseViewModel

    override fun onResume(owner: LifecycleOwner) {
        super<DefaultLifecycleObserver>.onResume(owner)
        onCreated()
    }

    private fun onCreated(){
//        setupProgressDialog(this, getViewModel().progressBar)
//        setupBackHandler(this, getViewModel().backEvent)
//        setupAppStringSRCErrorDialog(activity?.supportFragmentManager!! ,this, getViewModel().dialogError)
//        setupAppErrorDialog(activity?.supportFragmentManager!!, this, getViewModel().dialogErrorString)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity?.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    fun checkPermissionLocation(): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }
}