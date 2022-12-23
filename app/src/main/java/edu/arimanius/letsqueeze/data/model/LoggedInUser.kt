package edu.arimanius.letsqueeze.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: Int,
    val username: String,
    val displayName: String? = null,
)
