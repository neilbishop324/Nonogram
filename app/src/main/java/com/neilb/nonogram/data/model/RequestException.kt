package com.neilb.nonogram.data.model

class RequestException(val code: Int, message: String) : Throwable(message)