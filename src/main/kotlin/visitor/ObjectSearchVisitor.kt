package visitor

import model.*
class ObjectSearchVisitor(private val identifiers: List<String>) : Visitor {
    val results = mutableListOf<JsonObject>()

    override fun visit(jsonNumber: JsonNumber) { /* do nothing */ }
    override fun visit(jsonString: JsonString) { /* do nothing */ }
    override fun visit(jsonBoolean: JsonBoolean) { /* do nothing */ }
    override fun visit(jsonNull: JsonNull) { /* do nothing */ }

    override fun visit(jsonObject: JsonObject) {
        if (identifiers.all { id -> jsonObject[id] != null }) {
            results.add(jsonObject)
        }
        jsonObject.entries().forEach { (_, v) -> v.accept(this) }
    }

    override fun visit(jsonArray: JsonArray) {
        jsonArray.elements().forEach { it.accept(this) }
    }
}



