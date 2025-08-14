package com.example.share.core.database

interface DatabaseTransactionExecutor {
    suspend fun executeInTransaction(block: suspend () -> Unit)

}