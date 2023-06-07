package model


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonObjectTest {
    private var jsonObject: JsonObject = JsonObject(mutableMapOf())

    @Test
    fun testGetWhenKeyExistsForBooleanValue() {
        val jsonValue = JsonBoolean(true)
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testGetWhenKeyExistsForNumberValue() {
        val jsonValue = JsonNumber(42)
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testGetWhenKeyExistsForNullValue() {
        val jsonValue = JsonNull()
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testGetWhenKeyExistsForArrayValue() {
        val jsonValue = JsonArray(mutableListOf(JsonString("value")))
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testGetWhenKeyExistsForStringValue() {
        val jsonValue = JsonString("value")
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testGetWhenKeyDoesNotExist() {
        val retrievedValue = jsonObject["nonexistent"]

        assertNull(retrievedValue)
    }

    @Test
    fun testSetBooleanValue() {
        val jsonValue = JsonBoolean(true)
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun testSetNumberValue() {
        val jsonValue = JsonNumber(42)
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun `Test Set Null Value`() {
        val jsonValue = JsonNull()
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun `Test Set Array Value`() {
        val jsonValue = JsonArray(mutableListOf(JsonString("value")))
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun `Test Set String Value`() {
        val jsonValue = JsonString("value")
        jsonObject["key"] = jsonValue

        val retrievedValue = jsonObject["key"]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun `Test Entries Empty`() {
        val entries = jsonObject.entries()

        assertEquals(0, entries.size)
    }

    @Test
    fun `Test Entries Non Empty`() {
        val jsonValue1 = JsonBoolean(true)
        val jsonValue2 = JsonNumber(42)
        val jsonValue3 = JsonNull()
        val jsonValue4 = JsonArray(mutableListOf(JsonString("value")))
        val jsonValue5 = JsonString("value")

        jsonObject["key1"] = jsonValue1
        jsonObject["key2"] = jsonValue2
        jsonObject["key3"] = jsonValue3
        jsonObject["key4"] = jsonValue4
        jsonObject["key5"] = jsonValue5

        val entries = jsonObject.entries()

        assertEquals(5, entries.size)
        assertEquals("key1", entries.elementAt(0).key)
        assertEquals(jsonValue1, entries.elementAt(0).value)
        assertEquals("key2", entries.elementAt(1).key)
        assertEquals(jsonValue2, entries.elementAt(1).value)
        assertEquals("key3", entries.elementAt(2).key)
        assertEquals(jsonValue3, entries.elementAt(2).value)
        assertEquals("key4", entries.elementAt(3).key)
        assertEquals(jsonValue4, entries.elementAt(3).value)
        assertEquals("key5", entries.elementAt(4).key)
        assertEquals(jsonValue5, entries.elementAt(4).value)
    }

    @Test
    fun `Test To Json String Empty`() {
        val jsonString = jsonObject.toJsonString()

        assertEquals("{ }", jsonString)
    }

    @Test
    fun `Test To Json String Non Empty`() {
        val jsonValue1 = JsonBoolean(true)
        val jsonValue2 = JsonNumber(42)
        val jsonValue3 = JsonNull()
        val jsonValue4 = JsonArray(mutableListOf(JsonString("value")))
        val jsonValue5 = JsonString("value")

        jsonObject["key1"] = jsonValue1
        jsonObject["key2"] = jsonValue2
        jsonObject["key3"] = jsonValue3
        jsonObject["key4"] = jsonValue4
        jsonObject["key5"] = jsonValue5

        val jsonString = jsonObject.toJsonString()

        assertEquals(
            "{\"key1\": true, \"key2\": 42, \"key3\": null, \"key4\": [\"value\"], \"key5\": \"value\"}",
            jsonString
        )
    }
}
