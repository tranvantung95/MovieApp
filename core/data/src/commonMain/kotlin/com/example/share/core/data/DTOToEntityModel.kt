package com.example.share.core.data

import com.example.share.core.database.EntityModel

interface DTOToEntityModel<DTO : DTOModel, Entity : EntityModel> {
    fun mapToEntity(dto: DTO): Entity
    fun mapToEntityList(dtoList: List<DTO>): List<Entity> {
        return dtoList.map {
            mapToEntity(it)
        }
    }
}