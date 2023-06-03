package builder

import annotation.*
import model.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

class JsonBuilderTest {

    data class Student (
        val numero: Int,
        @JsonName("nome")
        val name: String,
        @JsonToString
        val internacional: Boolean
    )

    @JsonExclude
    data class ExcludedDataClass(val value: Int = 5)

    data class Course (
        @JsonName("uc")
        val name: String,
        val ects: Float,
        @JsonName("data-exame")
        val examDate: Any? = null,
        val inscritos: List<Student>
    )

    @Test
    fun `when buildJson called with data class, then returns JsonObject with correct values and structure`() {
        val students = listOf(
            Student(101101, "Dave Farley", true),
            Student(101102, "Martin Fowler", true),
            Student(26503, "André Santos", false)
        )
        val course = Course("PA", 6.0f, null, students)

        val result = JsonBuilder().buildJson(course)

        assertTrue(result is JsonObject)
        assertEquals(
            """{
            |"uc": "PA",
            |"ects": 6.0,
            |"data-exame": null,
            |"inscritos": [
            |  {"numero": 101101, "nome": "Dave Farley", "internacional": "true"},
            |  {"numero": 101102, "nome": "Martin Fowler", "internacional": "true"},
            |  {"numero": 26503, "nome": "André Santos", "internacional": "false"}
            |]
            |}""".trimMargin(), result.toJsonString()
        )
    }

    @Test
    fun `when buildJson called with excluded data class, then returns JsonObject with no members`() {
        val excludedClass = ExcludedDataClass()

        val result = JsonBuilder().buildJson(excludedClass)

        assertTrue(result is JsonObject)
        assertEquals("{}", result.toJsonString())
    }

    @Test
    fun `when buildJson called with map, then returns JsonObject with correct values and structure`() {
        val map = mapOf("key1" to "value1", "key2" to 2)

        val result = JsonBuilder().buildJson(map)

        assertTrue(result is JsonObject)
        assertEquals("""{"key1": "value1", "key2": 2}""", result.toJsonString())
    }

    @Test
    fun testBuildJson() {
        val builder = JsonBuilder()
        val json = builder.buildJson(listOf(1, 2, 3))
        assertTrue(json is JsonArray)
        assertEquals("[1, 2, 3]", json.toJsonString())
    }

    @Test
    fun testBuildJsonObject() {
        val builder = JsonBuilder()
        val json = builder.buildJson(mapOf("one" to 1, "two" to 2))
        assertTrue(json is JsonObject)
        assertEquals("{\"one\": 1, \"two\": 2}", json.toJsonString())
    }

    @Test
    fun testBuildJsonArray() {
        val builder = JsonBuilder()
        val json = builder.buildJson(listOf("apple", "banana", "cherry"))
        assertTrue(json is JsonArray)
        assertEquals("[\"apple\", \"banana\", \"cherry\"]", json.toJsonString())
    }

    @Test
    fun testBuildFromDataClass() {
        data class TestClass(val name: String, val age: Int)
        val builder = JsonBuilder()
        val json = builder.buildJson(TestClass("Alice", 30))
        assertTrue(json is JsonObject)
        assertEquals("{\"name\": \"Alice\", \"age\": 30}", json.toJsonString())
    }

    @Test
    fun testBuildFromDataClassWithJsonName() {
        data class TestClass(@JsonName("fullname") val name: String, val age: Int)
        val builder = JsonBuilder()
        val json = builder.buildJson(TestClass("Bob", 40))
        assertTrue(json is JsonObject)
        assertEquals("{\"fullname\": \"Bob\", \"age\": 40}", json.toJsonString())
    }

    @Test
    fun testBuildFromDataClassWithJsonExclude() {
        data class TestClass(val name: String, @JsonExclude val age: Int)
        val builder = JsonBuilder()
        val json = builder.buildJson(TestClass("Charlie", 50))
        assertTrue(json is JsonObject)
        assertEquals("{\"name\": \"Charlie\"}", json.toJsonString())
    }

    @Test
    fun testBuildFromDataClassWithJsonToString() {
        data class TestClass(val name: String, @JsonToString val age: Int)
        val builder = JsonBuilder()
        val json = builder.buildJson(TestClass("David", 60))
        assertTrue(json is JsonObject)
        assertEquals("{\"name\": \"David\", \"age\": \"60\"}", json.toJsonString())
    }
}
