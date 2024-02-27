package com.juan.superheroapp_bbdd.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juan.superheroapp_bbdd.data.SuperheroItemResponse

@Entity(tableName = "hero_table")
data class HeroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String
)

fun SuperheroItemResponse.toDatabase() = HeroEntity(name = name, image = superheroImage.url)