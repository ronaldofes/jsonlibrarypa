package model

// Repita um processo semelhante para outras classes
class JsonArray(private val list: MutableList<JsonValue>) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
        list.forEach { it.accept(visitor) }
    }
    override fun toString() : String {
        val jsonString = StringBuilder()
        jsonString.append("[ ")
        list.forEach {
            jsonString.append("$it, ")
        }
        if (list.isNotEmpty()) {
            jsonString.delete(jsonString.length - 2, jsonString.length) // Remove a última vírgula e espaço
        }
        jsonString.append(" ]")
        return jsonString.toString()
    }
}