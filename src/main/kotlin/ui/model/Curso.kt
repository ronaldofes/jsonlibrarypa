package ui.model

import annotation.JsonName

data class Curso(
    @JsonName("nome_curso")
    var nome: String
)
