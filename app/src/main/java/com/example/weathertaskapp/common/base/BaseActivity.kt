package com.example.weathertaskapp.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertaskapp.common.extension.setupErrorCustomDialog
import com.example.weathertaskapp.common.extension.setupErrorCustomStringDialog
import com.example.weathertaskapp.common.extension.setupProgressDialog

abstract class BaseActivity: AppCompatActivity() {
    abstract fun getViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupErrorCustomDialog(supportFragmentManager , this, getViewModel().dialogError)
        setupErrorCustomStringDialog(supportFragmentManager, this, getViewModel().dialogErrorString)
        setupProgressDialog(this, getViewModel().progressBar)
    }
}