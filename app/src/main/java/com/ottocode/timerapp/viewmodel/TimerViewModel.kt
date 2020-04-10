package com.ottocode.timerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

 val minute = MutableLiveData<String>()
 val hour = MutableLiveData<String>()
 val second = MutableLiveData<String>()
 val hourTime = MutableLiveData<String>()
 val minuteTime = MutableLiveData<String>()
 val secondTime = MutableLiveData<String>()

}