package model


class BeautyJson (private val propertyOrder: List<String> = emptyList()): Visitor {
    private val builder = StringBuilder()
    private var indent = 0

    private fun appendIndent() {
        for (i in 0 until indent) {
            builder.append("  ")
        }
    }

    override fun visit(jsonObject: JsonObject) {
        if (jsonObject.getPropertiesJson().isEmpty()) {
            builder.append("{ }")
            return
        }

        builder.append("{\n")
        indent++

        val properties = jsonObject.getPropertiesJson().entries.toList()

        val sortedProperties = properties.sortedWith(Comparator { e1, e2 ->
            val index1 = propertyOrder.indexOf(e1.key)
            val index2 = propertyOrder.indexOf(e2.key)
            when {
                index1 == -1 && index2 == -1 -> 0
                index1 == -1 -> 1
                index2 == -1 -> -1
                else -> index1.compareTo(index2)
            }
        })

        val iterator = sortedProperties.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next()

            appendIndent()
            builder.append("\"$key\": ")

            value.accept(this)

            if (iterator.hasNext()) {
                builder.append(",")
            }

            builder.append("\n")
        }

        indent--
        appendIndent()
        builder.append("}")
    }

    override fun visit(jsonArray: JsonArray) {
        if (jsonArray.elements().isEmpty()) {
            builder.append("[ ]")
            return
        }

        builder.append("[\n")
        indent++

        val iterator = jsonArray.elements().iterator()
        while (iterator.hasNext()) {
            val value = iterator.next()

            appendIndent()

            value.accept(this)

            if (iterator.hasNext()) {
                builder.append(",")
            }

            builder.append("\n")
        }

        indent--
        appendIndent()
        builder.append("]")
    }

    override fun visit(jsonString: JsonString) {
        builder.append("\"${jsonString.getValue()}\"")
    }

    override fun visit(jsonNumber: JsonNumber) {
        builder.append(jsonNumber.toJsonString())
    }

    override fun visit(jsonBoolean: JsonBoolean) {
        builder.append(jsonBoolean.toJsonString())
    }

    override fun visit(jsonNull: JsonNull) {
        builder.append(jsonNull.toJsonString())
    }

     fun build (jsonValue: JsonValue): String {
        builder.clear()
        jsonValue.accept(this)
        return builder.toString()
    }
}
