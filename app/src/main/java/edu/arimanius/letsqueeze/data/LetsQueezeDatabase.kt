package edu.arimanius.letsqueeze.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.arimanius.letsqueeze.data.converter.EnumConverter
import edu.arimanius.letsqueeze.data.converter.TimestampConverter
import edu.arimanius.letsqueeze.data.dao.*
import edu.arimanius.letsqueeze.data.entity.*

class DifficultyConverter : EnumConverter<Difficulty>()
class ThemeConverter : EnumConverter<Theme>()

@Database(
    entities = [
        User::class,
        Setting::class,
        Queeze::class,
        Category::class,
        Question::class,
        Answer::class,
        QueezeResult::class,
        SelectedAnswer::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    DifficultyConverter::class,
    TimestampConverter::class,
    ThemeConverter::class,
)
abstract class LetsQueezeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun settingDao(): SettingDao
    abstract fun queezeDao(): QueezeDao
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun queezeResultDao(): QueezeResultDao
    abstract fun selectedAnswerDao(): SelectedAnswerDao

    companion object {
        @Volatile
        private var instance: LetsQueezeDatabase? = null

        fun getInstance(context: Context): LetsQueezeDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LetsQueezeDatabase::class.java,
                    "letsqueeze_database"
                ).build().also { instance = it }
            }
        }
    }
}
