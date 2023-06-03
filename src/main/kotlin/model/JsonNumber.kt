package model

class JsonNumber(private val value: Number) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    override fun toString() : String {
        return "$value"
    }
}