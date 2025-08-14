package com.example.share.core.data

import com.example.share.core.database.EntityModel
import com.example.share.core.domain.DomainModel

interface EntityToDomainModel<Entity : EntityModel, Domain : DomainModel> {
    fun mapToDomain(entity: Entity): Domain
    fun mapToDomainList(entityList: List<Entity>): List<Domain> {
        return entityList.map {
            mapToDomain(it)
        }
    }
}