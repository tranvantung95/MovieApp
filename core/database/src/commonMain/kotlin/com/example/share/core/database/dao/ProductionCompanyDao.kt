package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.ProductionCompanyEntity

@Dao
interface ProductionCompanyDao {
    @Query("SELECT * FROM production_companies WHERE id = :companyId")
    suspend fun getProductionCompanyById(companyId: Int): ProductionCompanyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompanies(companies: List<ProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompany(company: ProductionCompanyEntity)
}