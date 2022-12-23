package edu.arimanius.letsqueeze.ui.profile

/**
 * User details post authentication that is exposed to the UI
 */
data class ProfileUserView(
    val username: String,
    val displayName: String,
    val phoneNumber: String,
)
