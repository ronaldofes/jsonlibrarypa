package ui.model

import annotation.JsonName
import annotation.JsonToString

data class Inscrito(

    @JsonName("nome_inscrito")
    var nome: String,

    @JsonName("numero_inscrito")
    @JsonToString
    var numero: String,

    @JsonName("internacional")
    var internacional: Boolean
)
