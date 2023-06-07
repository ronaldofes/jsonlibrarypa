package builder

import annotation.*
import model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findAnnotation

class JsonBuilderTest {
    private val jsonBuilder = JsonBuilder()

    data class DataClass(
        @JsonName("renamed")
        val name: String,

        @JsonExclude
        val excludeMe: String,

        @JsonToString
        val number: Int
    )

    @Test
    fun testBuildJsonString() {
        val result = jsonBuilder.buildJson("Test String")
        assertTrue(result is JsonString)
        assertEquals("Test String", (result as JsonString).getValue())
    }

    @Test
    fun testBuildJsonNumber() {
        val result = jsonBuilder.buildJson(123)
        assertTrue(result is JsonNumber)
        assertEquals(123, (result as JsonNumber).value)
    }

    @Test
    fun testBuildJsonBoolean() {
        val result = jsonBuilder.buildJson(true)
        assertTrue(result is JsonBoolean)
        assertEquals(true, (result as JsonBoolean).value)
    }

    @Test
    fun testBuildJsonArray() {
        val result = jsonBuilder.buildJson(listOf("Test String", 123, true))
        assertTrue(result is JsonArray)
        val array = result as JsonArray
        assertEquals(3, array.elements().size)
        assertTrue(array.elements()[0] is JsonString)
        assertTrue(array.elements()[1] is JsonNumber)
        assertTrue(array.elements()[2] is JsonBoolean)
    }

    @Test
    fun testBuildJsonObject() {
        val result = jsonBuilder.buildJson(mapOf("key1" to "value1", "key2" to 123, "key3" to true))
        assertTrue(result is JsonObject)
        val obj = result as JsonObject
        assertEquals(3, obj.entries().size)
        assertTrue(obj["key1"] is JsonString)
        assertTrue(obj["key2"] is JsonNumber)
        assertTrue(obj["key3"] is JsonBoolean)
    }

    @Test
    fun testBuildFromDataClass() {
        val result = jsonBuilder.buildJson(DataClass("Test String", "Exclude me", 123))
        assertTrue(result is JsonObject)
        val obj = result as JsonObject
        assertEquals(2, obj.entries().size)
        assertTrue(obj["renamed"] is JsonString)
        assertTrue(obj["number"] is JsonString)
        assertFalse(obj.entries().any { it.key == "excludeMe" })
    }

    @Test
    fun testJsonNameAnnotation() {
        val result = DataClass("Test String", "Exclude me", 123)::name.findAnnotation<JsonName>()
        assertNotNull(result)
        assertEquals("renamed", result?.name)
    }

    @Test
    fun testJsonExcludeAnnotation() {
        val result = DataClass("Test String", "Exclude me", 123)::excludeMe.findAnnotation<JsonExclude>()
        assertNotNull(result)
    }

    @Test
    fun testJsonToStringAnnotation() {
        val result = DataClass("Test String", "Exclude me", 123)::number.findAnnotation<JsonToString>()
        assertNotNull(result)
    }
}
