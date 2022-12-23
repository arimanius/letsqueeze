package edu.arimanius.letsqueeze.ui.register

import edu.arimanius.letsqueeze.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class RegisterResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
