package edu.arimanius.letsqueeze.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.entity.*
import edu.arimanius.letsqueeze.data.repository.SettingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(private val settingRepository: SettingRepository) : ViewModel() {
    private val _settingForm = MutableLiveData<SettingFormState>()
    val settingFormState: LiveData<SettingFormState> = _settingForm

    private val _saveResult = MutableLiveData<SaveResult>()
    val saveResult: LiveData<SaveResult> = _saveResult

    val setting = settingRepository.getSetting()
    val categories = settingRepository.getCategories()

    fun getCategoryById(id: Int): LiveData<Category?> {
        return settingRepository.getCategoryById(id)
    }

    fun save(theme: Theme, difficulty: Difficulty, numQuestion: Int, categoryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            settingRepository.save(theme, difficulty, numQuestion, categoryId)
            _saveResult.value = SaveResult(Setting(theme, difficulty, numQuestion, categoryId))
        }
    }

    fun numQuestionChanged(numQuestion: Int) {
        if (!isNumQuestionValid(numQuestion)) {
            _settingForm.value = SettingFormState(numQuestionError = R.string.invalid_num_question)
        } else {
            _settingForm.value = SettingFormState(isDataValid = true)
        }
    }

    private fun isNumQuestionValid(numQuestion: Int): Boolean {
        return numQuestion in 1..50
    }
}
