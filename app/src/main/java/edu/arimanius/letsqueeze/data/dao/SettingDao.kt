package edu.arimanius.letsqueeze.data.dao

import androidx.room.Dao
import edu.arimanius.letsqueeze.data.entity.Setting

@Dao
interface SettingDao: InsertableDao<Setting>
