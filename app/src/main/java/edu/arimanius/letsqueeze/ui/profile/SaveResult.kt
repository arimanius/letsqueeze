package edu.arimanius.letsqueeze.ui.profile

/**
 * Authentication result : success (user details) or error message.
 */
data class SaveResult(
    val success: ProfileUserView? = null,
    val error: Int? = null
)
