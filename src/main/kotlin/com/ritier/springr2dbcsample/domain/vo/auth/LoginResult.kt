package com.ritier.springr2dbcsample.domain.vo.auth

import com.ritier.springr2dbcsample.domain.model.UserCredential

sealed class LoginResult {
    data class Success(val credential: UserCredential) : LoginResult()
    object AccountInactive : LoginResult()
    object WrongPassword : LoginResult()
}
