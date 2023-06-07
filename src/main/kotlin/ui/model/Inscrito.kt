package ui.model

import annotation.JsonName
import annotation.JsonToString

data class Inscrito(

    @JsonName("nome")
    var nome: String,

    @JsonName("numero")
    @JsonToString
    var numero: String,

    @JsonName("internacional")
    var internacional: Boolean
)
