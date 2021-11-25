package com.lnunes.fizzbuzz

import com.lnunes.fizzbuzz.adapters.contract.FizzBuzzApiAdapter
import com.lnunes.fizzbuzz.adapters.impl.FizzBuzzApiAdapterImpl
import com.lnunes.fizzbuzz.domain.contracts.FizzBuzzApplication
import com.lnunes.fizzbuzz.domain.impl.FizzBuzzApplicationImpl

const val KEY = "Leandroscodetestwillbeawsome"
const val HOST = "https://codetest.jurosbaixos.com.br/v1"

suspend fun main(args: Array<String>) {
    val apiAdapter: FizzBuzzApiAdapter = FizzBuzzApiAdapterImpl(HOST, KEY)
    val app: FizzBuzzApplication = FizzBuzzApplicationImpl(apiAdapter)

    println("Starting application")
    app.run()
    println("Application finished")

    apiAdapter.close()
}

