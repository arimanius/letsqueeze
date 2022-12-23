package edu.arimanius.letsqueeze.data.prepopulate

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val database = LetsQueezeDatabase.getInstance(context)

        CoroutineScope(Dispatchers.IO).launch {
            // TODO: prepopulate
        }
    }
}
