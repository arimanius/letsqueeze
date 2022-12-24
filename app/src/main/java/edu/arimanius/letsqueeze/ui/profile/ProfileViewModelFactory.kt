package edu.arimanius.letsqueeze.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.data.repository.ProfileRepository

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                profileRepository = ProfileRepository(
                    LetsQueezeDatabase.getInstance(context).userDao(),
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
