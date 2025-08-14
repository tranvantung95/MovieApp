package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.ProductionCountryEntity

@Dao
interface ProductionCountryDao {
    @Query("SELECT * FROM production_countries WHERE iso31661 = :countryIso")
    suspend fun getProductionCountryByIso(countryIso: String): ProductionCountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountries(countries: List<ProductionCountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountry(country: ProductionCountryEntity)
}