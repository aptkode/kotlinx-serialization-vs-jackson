package com.aptkode.kotlinx.serialization

import com.aptkode.model.User
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KotlinXSerializationTest {
    @Test
    fun serializeTest(){
        val json = Json(JsonConfiguration.Stable)
        val jsonString = json.stringify(User.serializer(), User("tom", 21))
        assertEquals("""{"name":"tom","age":21}""", jsonString)
    }

    @Test
    fun deserializeTest(){
        val json = Json(JsonConfiguration.Stable)
        val (name, age) = json.parse(User.serializer(), """{"name":"john","age":22}""")
        assertEquals("john",name)
        assertEquals(22,age)
    }

    @Test
    fun serializeListTest(){
        val json = Json(JsonConfiguration.Stable)
        val jsonString = json.stringify(User.serializer().list, listOf(
                User("tom", 21),
                User("john", 22)
        ))
        assertEquals("""[{"name":"tom","age":21},{"name":"john","age":22}]""", jsonString)
    }
}