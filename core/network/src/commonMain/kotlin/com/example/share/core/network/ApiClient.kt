package com.example.share.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType

class ApiClient(private val httpClient: HttpClient) {
    suspend fun getData(vararg pathSegments: String, queryParams: Map<String, String> = emptyMap()): HttpResponse {
       return httpClient.get {
            pathSegments.forEach { segment ->
                url.appendPathSegments(segment)
            }
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }

    // POST request example
    suspend fun postData(endpoint: String, body: Any): HttpResponse {
        return httpClient.post(endpoint) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    // PUT request example
    suspend fun putData(endpoint: String, body: Any): HttpResponse {
        return httpClient.put(endpoint) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    // DELETE request example
    suspend fun deleteData(endpoint: String): HttpResponse {
        return httpClient.delete(endpoint)
    }

    // Cleanup
    fun close() {
        httpClient.close()
    }
}
