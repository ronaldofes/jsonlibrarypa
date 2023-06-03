package model

class JsonNull (val value: Any? = null) : JsonValue {
    override fun accept(visitor: Visitor) { visitor.visit(this) }
    override fun toJsonString(): String = "null"

}
