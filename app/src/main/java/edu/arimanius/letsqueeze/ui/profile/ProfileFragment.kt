package edu.arimanius.letsqueeze.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]

        val usernameEditText = binding.username
        val displayNameEditText = binding.displayName
        val phoneNumberEditText = binding.phoneNumber
        val saveButton = binding.save
        val loadingProgressBar = binding.loading

        profileViewModel.user.observe(viewLifecycleOwner) {
            usernameEditText.setText(it.username)
            displayNameEditText.setText(it.displayName)
            phoneNumberEditText.setText(it.phoneNumber)
        }

        profileViewModel.profileFormState.observe(viewLifecycleOwner) {
            it ?: return@observe
            saveButton.isEnabled = it.isDataValid
            it.usernameError?.let { usernameError ->
                usernameEditText.error = getString(usernameError)
            }
            it.displayNameError?.let { displayNameError ->
                displayNameEditText.error = getString(displayNameError)
            }
            it.phoneNumberError?.let { phoneNumberError ->
                phoneNumberEditText.error = getString(phoneNumberError)
            }
        }

        profileViewModel.saveResult.observe(viewLifecycleOwner) {
            it ?: return@observe
            loadingProgressBar.visibility = View.GONE
            it.error?.let { error ->
                showSaveFailed(error)
            }
            it.success?.let {
                showSaveSuccess()
            }
        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                profileViewModel.profileChanged(
                    usernameEditText.text.toString(),
                    displayNameEditText.text.toString(),
                    phoneNumberEditText.text.toString(),
                )
            }
        }

        usernameEditText.addTextChangedListener(afterTextChangedListener)
        displayNameEditText.addTextChangedListener(afterTextChangedListener)
        phoneNumberEditText.addTextChangedListener(afterTextChangedListener)
        phoneNumberEditText.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                profileViewModel.save(
                    usernameEditText.text.toString(),
                    displayNameEditText.text.toString(),
                    phoneNumberEditText.text.toString(),
                )
            }
            false
        }

        saveButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            profileViewModel.save(
                usernameEditText.text.toString(),
                displayNameEditText.text.toString(),
                phoneNumberEditText.text.toString(),
            )
        }
    }

    private fun showSaveSuccess() {
        Toast.makeText(
            requireContext(),
            "Profile saved",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showSaveFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}