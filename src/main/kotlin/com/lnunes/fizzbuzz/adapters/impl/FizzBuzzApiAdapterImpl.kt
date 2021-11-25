package com.lnunes.fizzbuzz.adapters.impl

import com.lnunes.fizzbuzz.adapters.impl.models.Success
import com.lnunes.fizzbuzz.adapters.contract.FizzBuzzApiAdapter
import com.lnunes.fizzbuzz.adapters.impl.models.Treasure
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

class FizzBuzzApiAdapterImpl(private val host: String, private val apiKey: String) : FizzBuzzApiAdapter {

    private val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
            })
        }
        defaultRequest {
            header("X-API-KEY", apiKey)
        }
    }

    override suspend fun reset() {
        httpClient.get<Success>("$host/fizzbuzz/reset")
    }

    override suspend fun getPuzzle(): List<Int> {
        return httpClient.get("$host/fizzbuzz")
    }

    override suspend fun postSolution(hash: String, solution: List<String>): Boolean {
        return try {
            httpClient.post<Success>("$host/fizzbuzz/$hash") {
                contentType(ContentType.Application.Json)
                body = solution
            }
            true
        } catch (e: ClientRequestException) {
            false
        }
    }

    override suspend fun checkTreasure(hash: String): String? {
        return try {
            val treasure: Treasure = httpClient.get("$host/fizzbuzz/$hash/canihastreasure")

            treasure.treasure
        } catch (e: ClientRequestException) {
            null
        }
    }

    override suspend fun delete(hash: String) {
        httpClient.delete<Success>("$host/fizzbuzz/$hash")
    }

    override fun close() {
        httpClient.close()
    }
}