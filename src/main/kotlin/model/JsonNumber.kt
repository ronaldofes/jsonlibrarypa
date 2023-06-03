package model

class JsonNumber(val value: Number) : JsonValue {
    override fun accept(visitor: Visitor) { visitor.visit(this) }
    override fun toJsonString(): String = value.toString()


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as JsonNumber

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}