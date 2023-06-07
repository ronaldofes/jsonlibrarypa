package integration

import builder.JsonBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Date

class JsonFactoryTest {
    private val builder = JsonBuilder()
    enum class TypeLocation { INT, EU, PORT }

    data class Student(val number: Int, val name: String, val isInternational: Boolean)
    data class StudentLocation(val type: TypeLocation, val number: Int, val name: String, val isInternational: Boolean)
    data class Course(
        val uc: String, val ects: Double, val dataExame: Date? = null, val registered: MutableList<Student>
    )

    @Test
    fun testValueObject() {
        val student = Student(101101, "Dave Farley", true)
        val jsonObject = builder.buildJson(student)

        val expectedJson = """
        {"isInternational": true, "name": "Dave Farley", "number": 101101}
    """.trimIndent()

        assertEquals(expectedJson, jsonObject.toJsonString())
    }

    @Test
    fun testCollection() {
        val student = Student(101101, "Dave Farley", true)
        val course = Course("PA", 6.0, null, mutableListOf(student))
        val jsonObject = builder.buildJson(course)
        val expectedJson = """
        {"ects": 6.0, "registered": [{"isInternational": true, "name": "Dave Farley", "number": 101101}], "uc": "PA"}
    """.trimIndent()

        assertEquals(expectedJson, jsonObject.toJsonString())
    }

    @Test
    fun testEnum() {
        val studentLocation = StudentLocation(TypeLocation.EU,101101, "Dave Farley", true)
        val jsonObject = builder.buildJson(studentLocation)

        val expectedJson = """
        {"isInternational": true, "name": "Dave Farley", "number": 101101, "type": "EU"}
    """.trimIndent()
        assertEquals(expectedJson, jsonObject.toJsonString())
    }
}