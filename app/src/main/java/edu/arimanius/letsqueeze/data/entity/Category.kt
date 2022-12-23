package edu.arimanius.letsqueeze.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
)
data class Category(
    @PrimaryKey
    var id: Int,
    var name: String,
) {
    override fun toString(): String {
        return name
    }
}
