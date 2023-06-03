package model

class JsonObject(private val members: MutableMap<String, JsonValue>) : JsonValue {
    operator fun get(key: String): JsonValue? = members[key]
    operator fun set(key: String, value: JsonValue) { members[key] = value }
    override fun accept(visitor: Visitor) { visitor.visit(this) }
    fun entries() = members.entries

    override fun toJsonString(): String {
        if(members.isEmpty()) return "{ }"
        return members.entries.joinToString(separator = ", ", prefix = "{", postfix = "}") {
            "\"${it.key}\": ${it.value.toJsonString()}"
        }
    }
}
