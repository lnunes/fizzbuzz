package com.lnunes.fizzbuzz.adapters.contract

/**
 * Fizz buzz api adapter
 */
interface FizzBuzzApiAdapter {

    /**
     * Resets the api to its initial state.
     */
    suspend fun reset()

    /**
     * Returns the fizzbuzz puzzle with an array of numbers
     */
    suspend fun getPuzzle(): List<Int>

    /**
     * Consumes the fizzbuzz answer from the earlier provided range of numbers
     *
     * @param hash
     * @param solution
     * @return true if the solution is valid
     */
    suspend fun postSolution(hash: String, solution: List<String>): Boolean

    /**
     * Might return you the treasure if you have put enough work into it.
     *
     * @param hash
     * @return treasure
     */
    suspend fun checkTreasure(hash: String): String?

    /**
     * Tells the API you are ready for the next range of numbers
     *
     * @param hash
     */
    suspend fun delete(hash: String)

    /**
     * Close adapter
     */
    fun close()
}