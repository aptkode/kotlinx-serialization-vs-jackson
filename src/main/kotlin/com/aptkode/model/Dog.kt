package com.aptkode.model

import kotlinx.serialization.Serializable

@Serializable
data class Dog(val sound: String = "Baw", val legs: Int = 4) : Animal() {
    override fun sound(): String {
        return sound
    }

    override fun legs(): Int {
        return legs
    }
}