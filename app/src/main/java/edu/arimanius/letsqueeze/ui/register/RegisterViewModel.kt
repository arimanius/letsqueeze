package edu.arimanius.letsqueeze.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.repository.UserRepository
import edu.arimanius.letsqueeze.data.repository.Result
import edu.arimanius.letsqueeze.ui.login.LoggedInUserView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, password: String) {
        // can be launched in a separate asynchronous job
        CoroutineScope(Dispatchers.IO).launch {
            val result = userRepository.register(username, password)

            if (result is Result.Success) {
                _registerResult.value =
                    RegisterResult(
                        success = LoggedInUserView(
                            displayName = result.data.displayName ?: result.data.username
                        )
                    )
            } else {
                _registerResult.value = RegisterResult(error = R.string.register_failed)
            }
        }
    }

    fun registerDataChanged(username: String, password: String, password2: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isPassword2Valid(password, password2)) {
            _registerForm.value = RegisterFormState(password2Error = R.string.invalid_password2)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isPassword2Valid(password: String, password2: String): Boolean {
        return password == password2
    }
}
