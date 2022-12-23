package edu.arimanius.letsqueeze.ui.register

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val password2Error: Int? = null,
    val isDataValid: Boolean = false,
)