package model

interface Visitor {
     fun visit(jsonObject: JsonObject)
     fun visit(jsonArray: JsonArray)
     fun visit(jsonString: JsonString)
     fun visit(jsonNumber: JsonNumber)
     fun visit(jsonBoolean: JsonBoolean)
     fun visit(jsonNull: JsonNull)
}
