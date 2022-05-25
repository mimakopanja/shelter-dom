package com.geekbrains.shelter_dom.utils.exceptions

import java.lang.Exception

open class ApiExceptions(
    override val message: String,
    val errorCode: Int? = null)
    : Exception()