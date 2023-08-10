package com.study.marketlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.marketlist.R
import com.study.marketlist.network.wrapper.ResultWrapper
import com.study.marketlist.presentation.UserPresentation
import com.study.marketlist.repository.UserRepository
import com.study.marketlist.util.ResourceWrapper
import com.study.marketlist.util.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val resourceWrapper: ResourceWrapper,
    private val repository: UserRepository
) : ViewModel() {
    private val _setErrorEmail = MutableLiveData<String>()
    val setErrorEmail: LiveData<String> = _setErrorEmail

    private val _setStatusButtonLogin = MutableLiveData<Boolean>()
    val setStatusButtonLogin: LiveData<Boolean> = _setStatusButtonLogin

    private val _setLoginError = MutableLiveData<String>()
    val setLoginError: LiveData<String> = _setLoginError

    private val _setLoginSuccess = MutableLiveData<UserPresentation>()
    val setLoginSuccess: LiveData<UserPresentation> = _setLoginSuccess

    private var isEmailFilled: Boolean = false
    private var isPasswordFilled: Boolean = false

    fun changeEmail(email: String) {
        validationEmail(email)
        isEmailFilled = email.isNotEmpty() && email.isValidEmail()
        setStatusButtonLogin()
    }

    fun changePassword(password: String) {
        isPasswordFilled = password.isNotEmpty()
        setStatusButtonLogin()
    }

    fun validationLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkLogin(
                email = email,
                password = password
            )
            when (result) {
                is ResultWrapper.Success -> {
                    result.content.let {
                        when {
                            it.error != null -> _setLoginError.postValue(it.error.message)
                            else -> _setLoginSuccess.postValue(it.user?.toUserPresentation())
                        }
                    }
                }

                is ResultWrapper.Error -> _setLoginError.postValue(result.error.message)
            }
        }
    }

    private fun validationEmail(email: String) {
        if (email.isValidEmail()) _setErrorEmail.postValue(EMPTY_STRING)
        else _setErrorEmail.postValue(resourceWrapper.getString(R.string.frag_login_et_email_error))
    }

    private fun setStatusButtonLogin() {
        _setStatusButtonLogin.postValue(isEmailFilled && isPasswordFilled)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}