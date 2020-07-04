package com.aptkode.model

import com.fasterxml.jackson.annotation.JsonTypeInfo
import kotlinx.serialization.Serializable

@Serializable
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="type")
abstract class Animal {
    abstract fun sound(): String
    abstract fun legs(): Int
}