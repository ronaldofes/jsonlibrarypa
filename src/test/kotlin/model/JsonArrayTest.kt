package model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import model.*
import org.junit.jupiter.api.Test


class JsonArrayTest {
    private var jsonArray: JsonArray = JsonArray(mutableListOf())

    @Test
    fun testGet() {
        val jsonValue = JsonString("value")
        jsonArray.add(jsonValue)

        val retrievedValue = jsonArray[0]

        assertEquals(jsonValue, retrievedValue)
    }

    @Test
    fun `Test Add`() {
        val jsonValue1 = JsonString("value1")
        val jsonValue2 = JsonString("value2")

        jsonArray.add(jsonValue1)
        jsonArray.add(jsonValue2)

        val elements = jsonArray.elements()

        assertEquals(2, elements.size)
        assertTrue(elements.contains(jsonValue1))
        assertTrue(elements.contains(jsonValue2))
    }


    @Test
    fun `Test To Json String Empty`() {
        val jsonString = jsonArray.toJsonString()

        assertEquals("[ ]", jsonString)
    }

    @Test
    fun`Test To Json String Non Empty`() {
        val jsonValue1 = JsonString("value1")
        val jsonValue2 = JsonString("value2")
        jsonArray.add(jsonValue1)
        jsonArray.add(jsonValue2)

        val jsonString = jsonArray.toJsonString()

        assertEquals("[\"value1\", \"value2\"]", jsonString)
    }
}
