package model

class JsonObject(private val map: MutableMap<String, JsonValue>) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        map.values.forEach { it.accept(visitor) }
    }

    // Convert JsonObject to String
    override fun toString() : String {
        val jsonString = StringBuilder()
        jsonString.append("{ ")
        map.forEach { (key, value) ->
            jsonString.append("\"$key\": $value, ")
        }
        if (map.isNotEmpty()) {
            jsonString.delete(jsonString.length - 2, jsonString.length) // Remove a última vírgula e espaço
        }
        jsonString.append(" }")
        return jsonString.toString()
    }
}