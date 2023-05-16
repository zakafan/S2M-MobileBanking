package com.example.s2m.util

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
 val client = HttpClient{
    install(ContentNegotiation){
        json(Json{
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}
}