package com.aptkode.model

import kotlinx.serialization.Serializable

@Serializable
abstract class Animal {
    abstract fun sound(): String
    abstract fun legs(): Int
}