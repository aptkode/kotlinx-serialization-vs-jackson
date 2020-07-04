package com.aptkode.kotlinx.serialization

import com.aptkode.model.Animal
import com.aptkode.model.Cat
import com.aptkode.model.Dog
import com.aptkode.model.User
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerializersModule
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KotlinXSerializationTest {
    @Test
    fun serializeTest() {
        val json = Json(JsonConfiguration.Stable)
        val jsonString = json.stringify(User.serializer(), User("tom", 21))
        assertEquals("""{"name":"tom","age":21}""", jsonString)
    }

    @Test
    fun deserializeTest() {
        val json = Json(JsonConfiguration.Stable)
        val (name, age) = json.parse(User.serializer(), """{"name":"john","age":22}""")
        assertEquals("john", name)
        assertEquals(22, age)
    }

    @Test
    fun serializeListTest() {
        val json = Json(JsonConfiguration.Stable)
        val jsonString = json.stringify(User.serializer().list, listOf(
                User("tom", 21),
                User("john", 22)
        ))
        assertEquals("""[{"name":"tom","age":21},{"name":"john","age":22}]""", jsonString)
    }

    @Test
    fun polymorphicSerializeTest() {
        val animalModule = SerializersModule {
            polymorphic(Animal::class) {
                Cat::class with Cat.serializer()
                Dog::class with Dog.serializer()
            }
        }
        val animals = listOf(Cat(), Cat(), Dog())
        val json = Json(JsonConfiguration.Stable, context = animalModule)
        val jsonString = json.stringify(Animal.serializer().list, animals)
        assertEquals("""[{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Dog","sound":"Baw","legs":4}]""", jsonString)
    }

    @Test
    fun polymorphicDeserializeTest() {
        val animalModule = SerializersModule {
            polymorphic(Animal::class) {
                Cat::class with Cat.serializer()
                Dog::class with Dog.serializer()
            }
        }
        val json = Json(JsonConfiguration.Stable, context = animalModule)
        val animals = json.parse(Animal.serializer().list, """[{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Dog","sound":"Baw","legs":4}]""")
        println(animals)
    }
}