package visitor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import model.*

public class ObjectSearchVisitorTest {

    private lateinit var json: JsonObject
    private lateinit var visitor: ObjectSearchVisitor

    @BeforeEach
    fun setUp() {
        json = JsonObject(mutableMapOf(
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
    }

    @Test
    fun `test search with all existing identifiers`() {
        visitor = ObjectSearchVisitor(listOf("uc", "ects", "data-exame", "inscritos"))
        json.accept(visitor)

        assertEquals(1, visitor.results.size)
        assertEquals(json, visitor.results[0])
    }

    @Test
    fun `test search with some non-existing identifiers`() {
        visitor = ObjectSearchVisitor(listOf("uc", "non-existing"))
        json.accept(visitor)

        assertTrue(visitor.results.isEmpty())
    }

    @Test
    fun `test search inside JsonArray elements`() {
        visitor = ObjectSearchVisitor(listOf("numero", "nome", "internacional"))
        json.accept(visitor)

        assertEquals(3, visitor.results.size)

        assertTrue(visitor.results.any { it["numero"] == JsonNumber(101101.0) })
        assertTrue(visitor.results.any { it["numero"] == JsonNumber(101102.0) })
        assertTrue(visitor.results.any { it["numero"] == JsonNumber(26503.0) })
    }

    @Test
    fun `test search with non-existing identifiers inside JsonArray`() {
        visitor = ObjectSearchVisitor(listOf("numero", "non-existing"))
        json.accept(visitor)

        assertTrue(visitor.results.isEmpty())
    }

}
