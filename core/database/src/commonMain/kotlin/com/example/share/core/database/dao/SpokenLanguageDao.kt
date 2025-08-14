package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.SpokenLanguageEntity

@Dao
interface SpokenLanguageDao {
    @Query("SELECT * FROM spoken_languages WHERE iso6391 = :languageIso")
    suspend fun getSpokenLanguageByIso(languageIso: String): SpokenLanguageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguages(languages: List<SpokenLanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguage(language: SpokenLanguageEntity)
}