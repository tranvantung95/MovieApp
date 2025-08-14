package com.example.share.core.database

import androidx.room.useWriterConnection

class RoomTransactionExecutor(private val database: MovieDatabase) : DatabaseTransactionExecutor {
    override suspend fun executeInTransaction(block: suspend () -> Unit) {
        database.useWriterConnection {
            executeInTransaction {
                block.invoke()
            }
        }
    }
}