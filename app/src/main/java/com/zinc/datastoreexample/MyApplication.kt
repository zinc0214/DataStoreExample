package com.zinc.datastoreexample

import android.app.Application
import com.zinc.datastoreexample.data.PreferenceDataStoreModule

class MyApplication : Application() {

    private lateinit var dataStore: PreferenceDataStoreModule

    companion object {
        private lateinit var myApplication: MyApplication
        fun getInstance(): MyApplication = myApplication
    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this
        dataStore = PreferenceDataStoreModule(this)
    }

    fun getDataStore(): PreferenceDataStoreModule = dataStore
}