package edu.arimanius.letsqueeze.data.repository

import androidx.lifecycle.LiveData
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.*
import edu.arimanius.letsqueeze.data.http.OpenTDBClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class SettingRepository(
    private val settingDao: SettingDao,
    private val categoryDao: CategoryDao,
) {

    fun getCategories(): LiveData<List<Category>> {
        CoroutineScope(Dispatchers.IO).launch {
            val categories = OpenTDBClient.getClient().getService().getCategories().await()
            for (category in categories.categories) {
                categoryDao.insert(Category(category.id, category.name))
            }
        }
        return categoryDao.getCategories()
    }

    fun getSetting(): LiveData<Setting> {
        return settingDao.get()
    }

    fun getCategoryById(id: Int): LiveData<Category?> {
        return categoryDao.getCategoryById(id)
    }

    suspend fun save(theme: Theme, difficulty: Difficulty, numQuestion: Int, categoryId: Int) {
        settingDao.insert(Setting(theme, difficulty, numQuestion, categoryId))
    }
}
