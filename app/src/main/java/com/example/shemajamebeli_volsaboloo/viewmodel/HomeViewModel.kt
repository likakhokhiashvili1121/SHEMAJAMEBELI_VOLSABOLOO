package com.example.shemajamebeli_volsaboloo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shemajamebeli_volsaboloo.data.DataStoreHandler

class HomeViewModel: ViewModel() {

    fun getPreferences() = DataStoreHandler.getPreferences()

    suspend fun clear() {
        DataStoreHandler.clear()
    }

    suspend fun remove(key: String) {
        DataStoreHandler.remove(key)
    }

}