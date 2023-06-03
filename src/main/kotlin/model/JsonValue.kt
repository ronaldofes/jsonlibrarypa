package model

interface JsonValue {
    fun accept(visitor: Visitor)
    fun toJsonString(): String
}