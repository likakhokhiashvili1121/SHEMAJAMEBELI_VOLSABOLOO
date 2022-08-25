package com.example.shemajamebeli_volsaboloo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shemajamebeli_volsaboloo.ResponseState
import com.example.shemajamebeli_volsaboloo.data.DataStoreHandler
import com.example.shemajamebeli_volsaboloo.model.AboutUser
import com.example.shemajamebeli_volsaboloo.model.LoginUser
import com.example.shemajamebeli_volsaboloo.model.RetrofitObj
import kotlinx.coroutines.flow.flow

class LoginViewModel: ViewModel() {

    fun getLoginFlow(userInfo: AboutUser) = flow<ResponseState> {
        emit(ResponseState.Loader(isLoading = true))
        val response = RetrofitObj.getAuthApi().getLoginForm(userInfo)
        if (response.isSuccessful && response.body() != null) {
            emit(ResponseState.Success<LoginUser>(response.body()!!))
        } else {
            emit(ResponseState.Error(response.errorBody()?.string() ?: "Error!"))
        }
        emit(ResponseState.Loader(isLoading = false))
    }

    suspend fun save(key: String, value: String) {
        DataStoreHandler.save(key, value)
    }

    fun getPreferences() = DataStoreHandler.getPreferences()

}