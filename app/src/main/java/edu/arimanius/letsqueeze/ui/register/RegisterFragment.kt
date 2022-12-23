package edu.arimanius.letsqueeze.ui.register

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import edu.arimanius.letsqueeze.databinding.FragmentRegisterBinding

import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.ui.login.LoggedInUserView

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(requireContext())
        )[RegisterViewModel::class.java]

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val password2EditText = binding.password2
        val registerButton = binding.register
        val loadingProgressBar = binding.loading

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormStateFormState ->
                if (registerFormStateFormState == null) {
                    return@Observer
                }
                registerButton.isEnabled = registerFormStateFormState.isDataValid
                registerFormStateFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                registerFormStateFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
                registerFormStateFormState.password2Error?.let {
                    password2EditText.error = getString(it)
                }
            })

        registerViewModel.registerResult.observe(viewLifecycleOwner,
            Observer { registerResultResult ->
                registerResultResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                registerResultResult.error?.let {
                    showRegisterFailed(it)
                }
                registerResultResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                registerViewModel.registerDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    password2EditText.text.toString(),
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        password2EditText.addTextChangedListener(afterTextChangedListener)
        password2EditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                )
            }
            false
        }

        registerButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            registerViewModel.register(
                usernameEditText.text.toString(),
                passwordEditText.text.toString(),
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}