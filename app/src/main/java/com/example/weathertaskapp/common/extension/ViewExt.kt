package com.example.weathertaskapp.common.extension

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.weathertaskapp.R
import com.example.weathertaskapp.common.utils.Event
import com.example.weathertaskapp.ui.dialogs.SimpleErrorMessageDialog

//TODO may produce nullPointerException
lateinit var dialog: AlertDialog

fun Fragment.showErrorDialog(errorText: String) {
    AlertDialog.Builder(context)
        .setMessage(errorText)
        .setPositiveButton(getString(R.string.ok_1)) { _, _ ->
        }
        .show()
}

fun Fragment.setupErrorDialog(
    lifecycleOwner: LifecycleOwner,
    errorDialogEvent: LiveData<Event<Int>>
) {
    errorDialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showErrorDialog(it.getString(res)) }
        }
    })
}

fun Fragment.setupErrorStringDialog(
    lifecycleOwner: LifecycleOwner,
    errorDialogStringEvent: LiveData<Event<String>>
) {
    errorDialogStringEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { error ->
            context?.let { showErrorDialog(error) }
        }
    })
}

fun Fragment.setupAppErrorDialog(
    supportFragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    errorDialogStringEvent: LiveData<Event<String>>
) {
    errorDialogStringEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { error ->
            context?.let { showAppErrorDialog(supportFragmentManager, error) }
        }
    })
}

fun Fragment.setupAppStringSRCErrorDialog(
    supportFragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    errorDialogStringEvent: LiveData<Event<Int>>
) {
    errorDialogStringEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showAppErrorDialog(supportFragmentManager, it.getString(res)) }
        }
    })
}

fun Fragment.showProgressDialog() {
    activity?.let {
        if (::dialog.isInitialized && !(context as Activity).isFinishing && !dialog.isShowing) {
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }
}

fun Fragment.hideProgressDialog() {
    activity?.let {
        try {
            if (::dialog.isInitialized && !(context as Activity).isFinishing && dialog.isShowing) dialog.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Fragment.setupProgressDialog(
    lifecycleOwner: LifecycleOwner,
    progressDialogEvent: LiveData<Event<Boolean>>
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
//    builder.setView(R.layout.loading_dialog)
    dialog = builder.create()
    progressDialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { isShow ->
            context?.let {
                if (isShow) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }
    })
}

fun Activity.setupProgressDialog(
    lifecycleOwner: LifecycleOwner,
    progressDialogEvent: LiveData<Event<Boolean>>
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
//    builder.setView(R.layout.loading_dialog)
    dialog = builder.create()
    progressDialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { isShow ->
            this?.let {
                if (isShow) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }
    })
}

fun Activity.showProgressDialog() {
    let {
        if (::dialog.isInitialized && !this.isFinishing && !dialog.isShowing) {
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }
}

fun Activity.hideProgressDialog() {
    let {
        try {
            if (::dialog.isInitialized && !this.isFinishing && dialog.isShowing) dialog.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Activity.setupErrorCustomDialog(
    supportFragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    errorDialogEvent: LiveData<Event<Int>>
) {
    errorDialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            showCustomErrorDialog(supportFragmentManager, this.getString(res))
        }
    })
}

fun Fragment.showAppErrorDialog(supportFragmentManager: FragmentManager, errorText: String) {
    val simpleMessageDialog = SimpleErrorMessageDialog()
    simpleMessageDialog.setDialogView(errorText, getString(R.string.ok_1), true, "", false)
    simpleMessageDialog.setPositiveClickListener {
        if (errorText == "Unauthorized") {
            context?.let {
                val intent = Intent(
                    activity, Class.forName("com.logickit.packer.feature.login.LoginActivity")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                activity?.startActivity(intent)
                activity?.finish()

            }
        } else {
            simpleMessageDialog.dismiss()
        }
    }
    try {
        simpleMessageDialog.show(
            supportFragmentManager,
            SimpleErrorMessageDialog::class.java.simpleName
        )
    } catch (ignored: IllegalStateException) {
        // There's no way to avoid getting this if saveInstanceState has already been called.
    }
}

fun Activity.showCustomErrorDialog(supportFragmentManager: FragmentManager, errorText: String) {
    val simpleMessageDialog = SimpleErrorMessageDialog()
    simpleMessageDialog.setDialogView(errorText, getString(R.string.ok_1), true, "", false)
    simpleMessageDialog.setPositiveClickListener {
        simpleMessageDialog.dismiss()
    }
    try {
//        simpleMessageDialog.showDialog()
        simpleMessageDialog.show(
            supportFragmentManager,
            SimpleErrorMessageDialog::class.java.simpleName
        )
    } catch (ignored: IllegalStateException) {
        // There's no way to avoid getting this if saveInstanceState has already been called.
    }
}

fun Activity.setupErrorCustomStringDialog(
    supportFragmentManager: FragmentManager,
    lifecycleOwner: LifecycleOwner,
    errorDialogStringEvent: LiveData<Event<String>>
) {
    errorDialogStringEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { error ->
            showCustomErrorDialog(supportFragmentManager, error)
        }
    })
}

fun Fragment.setupBackHandler(lifecycleOwner: LifecycleOwner, backEvent: LiveData<Event<Boolean>>) {
    backEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            context?.let { activity?.onBackPressed() }
        }
    })
}

fun Fragment.setupUnauthorizedHandler(
    lifecycleOwner: LifecycleOwner,
    unauthorizedEvent: LiveData<Event<Boolean>>
) {
    unauthorizedEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            context?.let {
                val intent = Intent(
                    activity, Class.forName("com.logickit.packer.feature.login.LoginActivity")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.startActivity(intent)
                activity?.finish()

            }
        }
    })
}
