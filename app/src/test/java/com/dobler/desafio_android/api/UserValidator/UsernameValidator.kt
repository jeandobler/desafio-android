package com.dobler.desafio_android.api.UserValidator

object UsernameValidator {

    private val trueValidations = listOf(
        Regex("[A-Za-z0-9-]"),
        Regex("^.{0,38}$"),
        Regex("^(?!.*--).+$")
    )

    private val falseValidations = listOf(
        Regex("[^A-Za-z0-9-]"),
        Regex("(-)$"),
        Regex("^(-)")
    )

    fun validate(username: String): Boolean {

        trueValidations.forEach {
            if (!username.contains(it)) {
                return false
            }
        }
        falseValidations.forEach {
            if (username.contains(it)) {
                return false
            }
        }

        return true
    }

}
