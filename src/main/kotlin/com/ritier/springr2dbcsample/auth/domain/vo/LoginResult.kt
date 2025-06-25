package com.ritier.springr2dbcsample.auth.domain.vo

import com.ritier.springr2dbcsample.user.domain.model.UserCredential

sealed class LoginResult {
    data class Success(val credential: UserCredential) : LoginResult()
    object AccountInactive : LoginResult()
    object WrongPassword : LoginResult()
}
