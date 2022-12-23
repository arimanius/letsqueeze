package edu.arimanius.letsqueeze.ui.setting

import edu.arimanius.letsqueeze.data.entity.Setting

/**
 * Authentication result : success (user details) or error message.
 */
data class SaveResult(
    val success: Setting? = null,
    val error: Int? = null
)
