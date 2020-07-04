package com.aptkode.model

import kotlinx.serialization.Serializable

@Serializable
data class Cat(private val sound: String = "meow", val legs: Int = 4) : Animal() {

    override fun sound(): String {
        return sound
    }

    override fun legs(): Int {
        return legs
    }
}