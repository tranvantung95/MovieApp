package com.example.share.core.data

import com.example.share.core.domain.DomainModel

interface DTOToDomainModel<DTO : DTOModel, Domain : DomainModel> {
    fun mapToDomain(dto: DTO): Domain
    fun mapToDomainList(dtoList: List<DTO>): List<Domain> {
        return dtoList.map {
            mapToDomain(it)
        }
    }
}