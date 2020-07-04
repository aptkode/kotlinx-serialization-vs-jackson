package com.aptkode.jackson.serialization

import com.aptkode.model.Animal
import com.aptkode.model.Cat
import com.aptkode.model.Dog
import com.aptkode.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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

    @Test
    fun polymorphicSerializeTest() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        val animals: List<Animal> = listOf(Cat(), Cat(), Dog())
        val jsonString = objectMapper
                .writerFor(objectMapper.typeFactory.constructCollectionType(List::class.java, Animal::class.java))
                .writeValueAsString(animals)
        assertEquals("""[{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Dog","sound":"Baw","legs":4}]""", jsonString)
    }

    @Test
    fun polymorphicDeserializeTest() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        val animals: Array<Animal> = objectMapper.readValue("""[{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Cat","sound":"meow","legs":4},{"type":"com.aptkode.model.Dog","sound":"Baw","legs":4}]""", Array<Animal>::class.java)
        assertEquals(3, animals.size)
        assertEquals("com.aptkode.model.Cat", animals[0].javaClass.typeName)
        assertEquals("com.aptkode.model.Dog", animals[2].javaClass.typeName)
    }

}