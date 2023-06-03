package builder

import annotation.*
import model.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.isAccessible

class JsonBuilder {
    fun buildJson(value: Any): JsonValue {
        return when(value) {
            is Collection<*> -> buildJsonArray(value)
            is Map<*, *> -> buildJsonObject(value)
            is String -> JsonString(value)
            is Number -> JsonNumber(value)
            is Boolean -> JsonBoolean(value)
            is Enum<*> -> JsonString(value.name)
            else -> buildFromDataClass(value)
        }
    }

    private fun buildJsonArray(collection: Collection<*>): JsonArray {
        val elements = collection.mapNotNull {
            if (it != null) buildJson(it) else null
        }
        return JsonArray(elements.toMutableList())
    }

    private fun buildJsonObject(map: Map<*, *>): JsonObject {
        val members = map.mapKeys {
            it.key.toString()
        }.mapValues {
            if (it.value != null) buildJson(it.value!!) else JsonNull()
        }.toMutableMap()

        return JsonObject(members)
    }

    private fun buildFromDataClass(data: Any): JsonValue {
        val kClass = data::class
        val members = mutableMapOf<String, JsonValue>()

        for (prop in kClass.declaredMemberProperties) {
            prop.isAccessible = true
            val value = prop.call(data)

            if (prop.findAnnotation<JsonExclude>() != null || value == null) continue

            val jsonName = prop.findAnnotation<JsonName>()?.name ?: prop.name
            val jsonValue = if (prop.findAnnotation<JsonToString>() != null && value is Number) {
                JsonString(value.toString())
            } else {
                buildJson(value)
            }

            members[jsonName] = jsonValue
        }

        return JsonObject(members)
    }
}
