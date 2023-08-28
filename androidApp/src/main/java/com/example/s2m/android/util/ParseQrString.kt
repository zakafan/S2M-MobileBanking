package com.example.s2m.android.util

fun parseString(input: String): Pair<String, String>? {
    val parts = input.split(";")
    return if (parts.size == 2) {
        Pair(parts[0], parts[1])
    } else {
        null
    }
}