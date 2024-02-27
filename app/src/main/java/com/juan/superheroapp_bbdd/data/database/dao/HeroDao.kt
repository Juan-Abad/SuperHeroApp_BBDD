package com.juan.superheroapp_bbdd.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juan.superheroapp_bbdd.data.database.entities.HeroDetailEntity
import com.juan.superheroapp_bbdd.data.database.entities.HeroEntity

@Dao
interface HeroDao {

    //hero_table functions
    @Query("SELECT * FROM hero_table")
    suspend fun getAllHero(): List<HeroEntity>

    @Query("SELECT * FROM hero_table WHERE name LIKE :name ORDER BY name DESC")
    suspend fun getHeroByName(name: String): List<HeroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hero: List<HeroEntity>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHero()

    @Query("SELECT * FROM hero_table WHERE id = :id")
    suspend fun getHeroById(id: String): HeroEntity

    @Query("DELETE FROM sqlite_sequence WHERE name LIKE 'hero_table'")
    suspend fun deletePrimaryKeyIndexHero()

    //HeroDetail_table functions
    @Query("DELETE FROM HeroDetail_table")
    suspend fun deleteAllHeroDetail()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHeroDetail(hero: List<HeroDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroDetail(hero: HeroDetailEntity)

    @Query("SELECT * FROM HeroDetail_table WHERE id = :id")
    suspend fun getHeroDetailById(id: Int): HeroDetailEntity

    @Query("SELECT * FROM HeroDetail_table")
    suspend fun getHeroDetail(): List<HeroDetailEntity>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'HeroDetail_table'")
    suspend fun deletePrimaryKeyIndexHeroDetail()
}