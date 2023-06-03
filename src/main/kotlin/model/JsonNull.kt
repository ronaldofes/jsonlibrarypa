package model

class JsonNull : JsonValue {
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
    override fun toString() : String {
        return "null"
    }
}
