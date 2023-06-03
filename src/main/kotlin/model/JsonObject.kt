package model

data class JsonObject(private val members: MutableMap<String, JsonValue> = mutableMapOf()) : JsonValue {

    operator fun get(key: String): JsonValue? = members[key]

    operator fun set(key: String, value: JsonValue) {
        members[key] = value
    }

    fun remove(key: String) {
        members.remove(key)
    }

    fun add(name: String, value: Any?) {
        members[name] = when (value) {
            null -> JsonNull()
            is Number -> JsonNumber(value)
            is String -> JsonString(value)
            is Boolean -> JsonBoolean(value)
            else -> throw IllegalArgumentException("Value type not supported")
        }
    }
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    fun entries() = members.entries.toSet()

    override fun toJsonString(): String {
        if (members.isEmpty()) return "{ }"
        return members.entries.joinToString(separator = ", ", prefix = "{", postfix = "}") {
            "\"${it.key}\": ${it.value.toJsonString()}"
        }
    }
    fun getString(name: String): String? {
        return (members[name] as? JsonString)?.getValue()
    }

    fun getNumber(name: String): Number? {
        return (members[name] as? JsonNumber)?.value
    }

    fun getBoolean(name: String): Boolean? {
        return (members[name] as? JsonBoolean)?.value
    }

    fun getNull(name: String): Any? {
        return (members[name] as? JsonNull)?.value
    }

    fun getArray(name: String): List<JsonValue>? {
        return (members[name] as? JsonArray)?.elements()
    }

    fun getPropertiesJson(): Map<String, JsonValue> {
        return members.toMap()
    }

    fun hasProperty(name: String): Boolean {
        return members.containsKey(name)
    }

    override fun toString() = toJsonString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JsonObject) return false

        if (members != other.members) return false

        return true
    }

    override fun hashCode(): Int {
        return members.hashCode()
    }
}
