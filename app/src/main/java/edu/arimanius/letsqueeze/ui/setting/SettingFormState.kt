package edu.arimanius.letsqueeze.ui.setting

/**
 * Data validation state of the login form.
 */
data class SettingFormState(
    val numQuestionError: Int? = null,
    val isDataValid: Boolean = false,
)