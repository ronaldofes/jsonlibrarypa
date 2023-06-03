package visitor

import model.*
import kotlin.reflect.KClass

class ArrayStructureVerificationVisitor(private val structure: JsonObject) : Visitor {
    var isValid = true
    private var arrayStructure: JsonObject? = null
    private var expectedTypeInArray: KClass<out JsonValue>? = null

    override fun visit(jsonNumber: JsonNumber) {
        if (expectedTypeInArray != null && expectedTypeInArray != jsonNumber::class) {
            isValid = false
        }
    }

    override fun visit(jsonString: JsonString) {
        if (expectedTypeInArray != null && expectedTypeInArray != jsonString::class) {
            isValid = false
        }
    }

    override fun visit(jsonBoolean: JsonBoolean) {
        if (expectedTypeInArray != null && expectedTypeInArray != jsonBoolean::class) {
            isValid = false
        }
    }

    override fun visit(jsonNull: JsonNull) {
        if (expectedTypeInArray != null && expectedTypeInArray != jsonNull::class) {
            isValid = false
        }
    }

    override fun visit(jsonObject: JsonObject) {
        if (arrayStructure != null) {
            validateJsonObjectStructure(jsonObject, arrayStructure!!)
        } else {
            jsonObject.entries().forEach { (_, v) -> v.accept(this) }
        }
    }

    override fun visit(jsonArray: JsonArray) {
        val expectedArrayStructure = structure.entries().firstOrNull { it.value is JsonArray }?.value as? JsonArray
        expectedTypeInArray = expectedArrayStructure?.elements()?.firstOrNull()?.javaClass?.kotlin

        jsonArray.elements().forEach { element ->
            if (element::class != expectedTypeInArray) {
                isValid = false
                return
            }
        }
        expectedTypeInArray = null
        arrayStructure = null
    }

    private fun validateJsonObjectStructure(jsonObject: JsonObject, structure: JsonObject) {
        structure.entries().forEach { (key, value) ->
            val objectEntry = jsonObject[key]
            if (objectEntry == null || objectEntry::class != value::class) {
                isValid = false
                return
            }
        }
    }
}
