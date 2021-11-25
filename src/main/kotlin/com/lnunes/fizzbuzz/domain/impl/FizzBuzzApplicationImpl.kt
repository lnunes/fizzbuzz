package com.lnunes.fizzbuzz.domain.impl

import com.lnunes.fizzbuzz.adapters.contract.FizzBuzzApiAdapter
import com.lnunes.fizzbuzz.domain.contracts.FizzBuzzApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.MessageDigest

class FizzBuzzApplicationImpl(private val apiAdapter: FizzBuzzApiAdapter) : FizzBuzzApplication {

    /**
     * Run application
     */
    override suspend fun run() {
        apiAdapter.reset()

        while (true) {
            val puzzle = apiAdapter.getPuzzle()
            val solution = puzzle.resolveFizzBuzz()

            val hash = Json.encodeToString(solution).toSHA256()

            val validSolution = apiAdapter.postSolution(hash, solution)
            if (!validSolution) {
                println("Invalid Solution")
                return
            }

            val treasure = apiAdapter.checkTreasure(hash)
            if (treasure != null) {
                println("Treasure found! Hash: $hash")
                println(treasure)
                return
            } else {
                println("Treasure not found. Hash: $hash")
            }

            apiAdapter.delete(hash)
        }
    }

    private fun List<Int>.resolveFizzBuzz(): List<String> {
        return this.map {
            if (it % 3 == 0 && it % 5 == 0) {
                "fizzbuzz"
            } else if (it % 3 == 0) {
                "fizz"
            } else if (it % 5 == 0) {
                "buzz"
            } else {
                it.toString()
            }
        }
    }

    private fun String.toSHA256(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}