package visitor

import model.*
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ArrayStructureVerificationVisitorTest {

    private val json: JsonObject = JsonObject(mutableMapOf(
        "uc" to JsonString("PA"),
        "ects" to JsonNumber(6.0),
        "data-exame" to JsonNull(),
        "inscritos" to JsonArray(mutableListOf(
            JsonObject(mutableMapOf(
                "numero" to JsonNumber(101101.0),
                "nome" to JsonString("Dave Farley"),
                "internacional" to JsonBoolean(true)
            )),
            JsonObject(mutableMapOf(
                "numero" to JsonNumber(101102.0),
                "nome" to JsonString("Martin Fowler"),
                "internacional" to JsonBoolean(true)
            )),
            JsonObject(mutableMapOf(
                "numero" to JsonNumber(26503.0),
                "nome" to JsonString("André Santos"),
                "internacional" to JsonBoolean(false)
            ))
        ))
    ))

    @Test
    fun testStructureIsValid() {
        val structure = JsonObject(mutableMapOf(
            "uc" to JsonString(""),
            "ects" to JsonNumber(0),
            "data-exame" to JsonNull(),
            "inscritos" to JsonArray(mutableListOf(
                JsonObject(mutableMapOf(
                    "numero" to JsonNumber(0),
                    "nome" to JsonString(""),
                    "internacional" to JsonBoolean(false)
                ))
            ))
        ))
        val visitor = ArrayStructureVerificationVisitor(structure)
        json.accept(visitor)
        assertTrue(visitor.isValid)
    }

    @Test
    fun testStructureIsNotValid() {
        val structure = JsonObject(mutableMapOf(
            "uc" to JsonString(""),
            "ects" to JsonNumber(0),
            "data-exame" to JsonNull(),
            "inscritos" to JsonArray(mutableListOf(
                JsonObject(mutableMapOf(
                    "numero" to JsonString(""),
                    "nome" to JsonString(""),
                    "internacional" to JsonBoolean(false)
                ))
            ))
        ))
        val visitor = ArrayStructureVerificationVisitor(structure)
        json.accept(visitor)
        assertFalse(visitor.isValid)
    }
}
