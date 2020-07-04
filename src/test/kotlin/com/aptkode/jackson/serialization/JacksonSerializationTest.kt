package com.aptkode.jackson.serialization

import com.aptkode.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JacksonSerializationTest {

    @Test
    fun serializeTest() {
        val objectMapper = ObjectMapper()
        val jsonString = objectMapper.writeValueAsString(User("tom", 21))
        assertEquals("""{"name":"tom","age":21}""", jsonString)
    }

    @Test
    fun deserializeTest() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        val (name, age) = objectMapper.readValue("""{"name":"john","age":22}""", User::class.java)
        assertEquals("john", name)
        assertEquals(22, age)
    }

    @Test
    fun serializeListTest() {
        val objectMapper = ObjectMapper()
        val jsonString = objectMapper.writeValueAsString(listOf(
                User("tom", 21),
                User("john", 22)
        ))
        assertEquals("""[{"name":"tom","age":21},{"name":"john","age":22}]""", jsonString)
    }

    @Test
    fun deserializeListTest() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        val users = objectMapper.readValue("""[{"name":"tom","age":21},{"name":"john","age":22}]""", Array<User>::class.java)
        assertEquals(2, users.size)
    }

}