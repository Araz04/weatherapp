package com.example.weathertaskapp.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weathertaskapp.common.extension.setupProgressDialog

abstract class BaseActivity: AppCompatActivity() {
    abstract fun getViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgressDialog(this, getViewModel().progressBar)
    }
}