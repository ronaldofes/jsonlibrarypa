package visitor

import StructureVerificationVisitor
import model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

public class StructureVerificationVisitorTest {

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
    fun `test if string type is correctly identified`() {
        val visitor = StructureVerificationVisitor("uc", JsonString::class)
        json.accept(visitor)

        Assertions.assertTrue(visitor.isValid)
    }

    @Test
    fun `test if number type is correctly identified`() {
        val visitor = StructureVerificationVisitor("ects", JsonNumber::class)
        json.accept(visitor)

        Assertions.assertTrue(visitor.isValid)
    }

    @Test
    fun `test if null type is correctly identified`() {
        val visitor = StructureVerificationVisitor("data-exame", JsonNull::class)
        json.accept(visitor)

        Assertions.assertTrue(visitor.isValid)
    }

    @Test
    fun `test if array type is correctly identified`() {
        val visitor = StructureVerificationVisitor("inscritos", JsonArray::class)
        json.accept(visitor)

        Assertions.assertTrue(visitor.isValid)
    }

    @Test
    fun `test if wrong type is correctly identified`() {
        val visitor = StructureVerificationVisitor("uc", JsonNumber::class)
        json.accept(visitor)

        Assertions.assertFalse(visitor.isValid)
    }

    @Test
    fun `test if missing type is correctly identified`() {
        val visitor = StructureVerificationVisitor("missing", JsonString::class)
        json.accept(visitor)

        Assertions.assertFalse(visitor.isValid)
    }
}
