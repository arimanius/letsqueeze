package edu.arimanius.letsqueeze.ui.setting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StringRes
import edu.arimanius.letsqueeze.data.entity.Category
import edu.arimanius.letsqueeze.data.entity.Difficulty
import edu.arimanius.letsqueeze.data.entity.Theme
import edu.arimanius.letsqueeze.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel = ViewModelProvider(
            this,
                SettingViewModelFactory(requireContext())
        )[SettingViewModel::class.java]

        val themeSpinner = binding.theme
        val difficultySpinner = binding.difficulty
        val numQuestionEditText = binding.numQuestion
        val categorySpinner = binding.category
        val saveButton = binding.save
        val loadingProgressBar = binding.loading

        themeSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Theme.values()
        )
        difficultySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Difficulty.values()
        )

        settingViewModel.setting.observe(viewLifecycleOwner) { setting ->
            setting ?: return@observe
            themeSpinner.setSelection(setting.theme.ordinal)
            difficultySpinner.setSelection(setting.difficulty.ordinal)
            numQuestionEditText.setText(setting.numQuestion.toString())
            settingViewModel.categories.observe(viewLifecycleOwner) { categories ->
                categorySpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    categories
                )
                val category = settingViewModel.getCategoryById(setting.categoryId)
                category.observe(viewLifecycleOwner) {
                    categorySpinner.setSelection(categories.indexOf(it))
                }
            }
        }

        settingViewModel.settingFormState.observe(viewLifecycleOwner) { settingFormState ->
            settingFormState ?: return@observe
            settingFormState.numQuestionError?.let {
                numQuestionEditText.error = getString(it)
            }
        }

        settingViewModel.saveResult.observe(viewLifecycleOwner) { saveResult ->
            saveResult ?: return@observe
            if (saveResult.error != null) {
                showSaveFailed(saveResult.error)
            }
            if (saveResult.success != null) {
                showSaveSuccess()
            }
        }

        numQuestionEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                settingViewModel.numQuestionChanged(
                    numQuestionEditText.text.toString(),
                )
            }
        })

        saveButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            settingViewModel.save(
                themeSpinner.selectedItem as Theme,
                difficultySpinner.selectedItem as Difficulty,
                numQuestionEditText.text.toString().toInt(),
                (categorySpinner.selectedItem as Category).id,
            )
        }
    }

    private fun showSaveSuccess() {
        Toast.makeText(
            requireContext(),
            "Setting saved",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showSaveFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
