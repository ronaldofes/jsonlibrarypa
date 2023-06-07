import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import model.*

class JsonObjIntTest {

    private lateinit var jsonObject: JsonObject

    @BeforeEach
    fun setUp() {
        jsonObject = JsonObject(mutableMapOf())
    }

    @Test
    fun testAddProperty() {
        jsonObject.add("name", "Martin")
        assertEquals("\"Martin\"", jsonObject["name"].toString())
    }

    @Test
    fun testSetString() {
        jsonObject.set("name", JsonString("Dave Farley"))
        assertEquals("\"Dave Farley\"", jsonObject.get("name").toString())
    }

    @Test
    fun testSetNumber() {
        jsonObject.set("ects", JsonNumber(6.0))
        val result : JsonNumber = jsonObject["ects"] as JsonNumber
        assertEquals(6.0, result.value)
    }

    @Test
    fun testSetBoolean() {
        jsonObject.set("international", JsonBoolean(false))
        val result : JsonBoolean = jsonObject["international"] as JsonBoolean
        assertEquals(false, result.value)
    }

    @Test
    fun testSetNull() {
        jsonObject.set("data-exame", JsonNull())
        val result : JsonNull = jsonObject["data-exame"] as JsonNull
        assertEquals(null, result.value)
    }

    @Test
    fun testSetArray() {
        val jsonArray = mutableListOf<JsonValue>()
        jsonArray.add(JsonString("numero"))
        jsonArray.add(JsonString("nome"))
        jsonObject.set("inscritos", JsonArray(jsonArray))
        val result = jsonObject.get("inscritos") as JsonArray
        assertEquals(jsonArray, result.elements())
    }

    @Test
    fun testRemoveProperty() {
        jsonObject["nome"] = JsonString("Martin Fowler")
        jsonObject.remove("nome")
        assertEquals("null", jsonObject["name"].toString())
    }

    @Test
    fun testPrimitiveTypes() {
        val jsonObject = JsonObject(mutableMapOf())
        jsonObject.add("int", 1)
        jsonObject.add("double", 2.5)
        jsonObject.add("float", 3.14f)
        jsonObject.add("long", 1234567890L)
        jsonObject.add("short", 32767.toShort())
        jsonObject.add("byte", 127.toByte())
        jsonObject.add("boolean", true)

        val expectedJson = """
        {"int": 1, "double": 2.5, "float": 3.14, "long": 1234567890, "short": 32767, "byte": 127, "boolean": true}
    """.trimIndent()

        assertEquals(expectedJson, jsonObject.toJsonString())
    }

}