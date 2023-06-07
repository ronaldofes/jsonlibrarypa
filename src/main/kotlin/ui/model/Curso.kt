package ui.model

import annotation.JsonName

data class Curso(
    @JsonName("nome")
    var nome: String
)
