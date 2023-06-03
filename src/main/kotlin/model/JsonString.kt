package model

class JsonString(private val value: String) : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    override fun toString() : String {
        return "\"$value\""
    }
}