package model

class JsonBoolean(val value: Boolean) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    override fun toJsonString(): String = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as JsonBoolean

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}