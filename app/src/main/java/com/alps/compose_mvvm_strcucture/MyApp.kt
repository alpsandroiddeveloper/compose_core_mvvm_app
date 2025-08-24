package com.alps.compose_mvvm_strcucture

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"Dependency injection implemented successfully")
    }

    companion object{
        const val TAG = "MyApp"
    }

}