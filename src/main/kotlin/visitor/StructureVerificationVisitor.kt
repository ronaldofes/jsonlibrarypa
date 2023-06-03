import model.*
import kotlin.reflect.KClass

class StructureVerificationVisitor(private val identifier: String, private val type: KClass<*>) : Visitor {
    var isValid = false

    override fun visit(jsonNumber: JsonNumber) { /* do nothing */ }
    override fun visit(jsonString: JsonString) { /* do nothing */ }
    override fun visit(jsonBoolean: JsonBoolean) { /* do nothing */ }
    override fun visit(jsonNull: JsonNull) { /* do nothing */ }

    override fun visit(jsonObject: JsonObject) {
        jsonObject[identifier]?.let {
            if (it::class == type) isValid = true
        }
        if (!isValid) {
            jsonObject.entries().forEach { (_, v) ->
                if (v is JsonObject) {
                    v.accept(this)
                } else if (v is JsonArray) {
                    v.accept(this)
                }
            }
        }
    }

    override fun visit(jsonArray: JsonArray) {
        jsonArray.elements().forEach {
            if (it is JsonObject) {
                it.accept(this)
            } else {
                isValid = false
            }
        }
    }
}


