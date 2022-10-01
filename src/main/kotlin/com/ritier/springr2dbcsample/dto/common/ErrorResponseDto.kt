package com.ritier.springr2dbcsample.dto.common

data class ErrorResponseDto(val error : CommonError){

}

data class CommonError(
    val code : Long,
    val message : String,
    val type : String,
)
