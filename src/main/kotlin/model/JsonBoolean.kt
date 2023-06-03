package model

class JsonBoolean(private val value: Boolean) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    override fun toString() : String {
        return "$value"
    }
}