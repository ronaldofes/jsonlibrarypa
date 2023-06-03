package model
class JsonArray(private val elements: MutableList<JsonValue>) : JsonValue {
    operator fun get(i: Int): JsonValue = elements[i]
    fun add(element: JsonValue) { elements.add(element) }
    override fun accept(visitor: Visitor) { visitor.visit(this) }
    fun elements() = elements

    override fun toJsonString(): String {
        if (elements.isEmpty()) return "[ ]"
        return elements.joinToString(separator = ", ", prefix = "[", postfix = "]") {
            it.toJsonString()
        }
    }

}

