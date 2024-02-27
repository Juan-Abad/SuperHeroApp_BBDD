package com.juan.superheroapp_bbdd.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juan.superheroapp_bbdd.data.database.dao.HeroDao
import com.juan.superheroapp_bbdd.data.database.entities.HeroDetailEntity
import com.juan.superheroapp_bbdd.data.database.entities.HeroEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [HeroEntity::class, HeroDetailEntity::class], version = 1)
abstract class HeroDatabase: RoomDatabase() {

    abstract fun getHeroDao(): HeroDao
}