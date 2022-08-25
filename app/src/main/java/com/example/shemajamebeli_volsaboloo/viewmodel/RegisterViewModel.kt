package com.example.shemajamebeli_volsaboloo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shemajamebeli_volsaboloo.ResponseState
import com.example.shemajamebeli_volsaboloo.model.AboutUser
import com.example.shemajamebeli_volsaboloo.model.RegisterUser
import com.example.shemajamebeli_volsaboloo.model.RetrofitObj
import kotlinx.coroutines.flow.flow

class RegisterViewModel: ViewModel() {

    fun getRegisterFlow(userInfo: AboutUser) = flow<ResponseState> {
        emit(ResponseState.Loader(isLoading = true))
        val response = RetrofitObj.getAuthApi().getRegisterForm(userInfo)
        if (response.isSuccessful && response.body() != null) {
            emit(ResponseState.Success<RegisterUser>(response.body()!!))
        } else {
            emit(ResponseState.Error(response.errorBody()?.string() ?: "Error!"))
        }
        emit(ResponseState.Loader(isLoading = false))
    }

}