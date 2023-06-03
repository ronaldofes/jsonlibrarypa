package model

class JsonString(private val value: String) : JsonValue {
    override fun accept(visitor: Visitor) {visitor.visit(this) }
    fun getValue(): String = value
    override fun toJsonString() : String { return "\"$value\"" }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as JsonString

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}