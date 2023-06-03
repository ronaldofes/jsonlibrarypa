package visitor

import model.*

class ArrayStructureVerificationVisitor(private val structure: JsonObject) : Visitor {
    var isValid = true

    override fun visit(jsonNumber: JsonNumber) { /* do nothing */ }
    override fun visit(jsonString: JsonString) { /* do nothing */ }
    override fun visit(jsonBoolean: JsonBoolean) { /* do nothing */ }
    override fun visit(jsonNull: JsonNull) { /* do nothing */ }

    override fun visit(jsonObject: JsonObject) {

        if (jsonObject.entries().any { it.key !in structure.entries().map { it.key } } ||
            structure.entries().any { it.key !in jsonObject.entries().map { it.key } || it.value::class != jsonObject[it.key]!!::class }) {
            isValid = false
        }

        if (isValid) {
            jsonObject.entries().forEach { (_, v) -> v.accept(this) }
        }
    }

    override fun visit(jsonArray: JsonArray) {
        jsonArray.elements().forEach { it.accept(this) }
    }
}

