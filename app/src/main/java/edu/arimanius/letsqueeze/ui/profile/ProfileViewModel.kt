package edu.arimanius.letsqueeze.ui.profile

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private val _profileForm = MutableLiveData<ProfileFormState>()
    val profileFormState: LiveData<ProfileFormState> = _profileForm

    private val _saveResult = MutableLiveData<SaveResult>()
    val saveResult: LiveData<SaveResult> = _saveResult

    val user = profileRepository.getUser()

    fun save(username: String, displayName: String, phoneNumber: String) {
        CoroutineScope(Dispatchers.IO).launch {
            profileRepository.save(username, displayName, phoneNumber)
            _saveResult.value = SaveResult(ProfileUserView(username, displayName, phoneNumber))
        }
    }

    fun profileChanged(username: String, displayName: String, phoneNumber: String) {
        if (!isUsernameValid(username)) {
            _profileForm.value = ProfileFormState(usernameError = R.string.invalid_username)
        } else if (displayName.isBlank()) {
            _profileForm.value = ProfileFormState(displayNameError = R.string.invalid_display_name)
        } else if (!isPhoneNumberValid(phoneNumber)) {
            _profileForm.value = ProfileFormState(phoneNumberError = R.string.invalid_phone_number)
        } else {
            _profileForm.value = ProfileFormState(isDataValid = true)
        }
    }

    private fun isUsernameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return Patterns.PHONE.matcher(phoneNumber).matches()
    }
}
