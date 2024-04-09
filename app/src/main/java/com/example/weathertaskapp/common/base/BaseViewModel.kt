package com.example.weathertaskapp.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.weathertaskapp.common.utils.Event
import com.example.weathertaskapp.navigation.NavigationCommand

open class BaseViewModel  : ViewModel()  {

    // FOR SNACKBAR ERROR HANDLER
    private val _progressBar = MutableLiveData<Event<Boolean>>()
    val progressBar: LiveData<Event<Boolean>> get() = _progressBar

    protected val _snackBarSecondaryMessage = MutableLiveData<Event<Int>>()
    val snackBarSecondaryMessage: LiveData<Event<Int>> get() = _snackBarSecondaryMessage

    // FOR ERROR DIALOG HANDLER
    private val _dialogError = MutableLiveData<Event<Int>>()
    val dialogError: LiveData<Event<Int>> get() = _dialogError

    // FOR ERROR DIALOG STRING HANDLER
    private val _dialogErrorString = MutableLiveData<Event<String>>()
    val dialogErrorString: LiveData<Event<String>> get() = _dialogErrorString

    // FOR NAVIGATION
    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    // FOR BACK HANDLER
    private val _backEvent = MutableLiveData<Event<Boolean>>()
    val backEvent: LiveData<Event<Boolean>> get() = _backEvent

    // FOR UNAUTHORIZED HANDLER
    private val _unauthorizedEvent = MutableLiveData<Event<Boolean>>()
    val unauthorizedEvent: LiveData<Event<Boolean>> get() = _unauthorizedEvent
    /**
     * Convenient method to handle navigation from a [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }

    fun showProgressBar() {
        _progressBar.value = Event(true)
    }

    fun showProgressBarActivity() {
        _progressBar.value = Event(true)
    }

    fun hideProgressBarActivity() {
        _progressBar.value = Event(false)
    }

    fun hideProgressBar() {
        _progressBar.value = Event(false)
    }

    fun showErrorDialog(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showErrorDialog(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun showAppErrorDialog(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showAppErrorDialog(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun showAppErrorDialogActivity(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showAppErrorDialogActivity(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun onBackPress() {
        _backEvent.value = Event(true)
    }

    fun isUnauthorized() {
        _unauthorizedEvent.value = Event(true)
    }
}