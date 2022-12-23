package edu.arimanius.letsqueeze.ui.profile

/**
 * Data validation state of the login form.
 */
data class ProfileFormState(
    val usernameError: Int? = null,
    val displayNameError: Int? = null,
    val phoneNumberError: Int? = null,
    val isDataValid: Boolean = false,
)
