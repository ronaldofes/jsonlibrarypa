package ui.model

import annotation.JsonName
import annotation.JsonToString

data class Modelo(
    @JsonName("uc")
    var uc: String,

    @JsonName("ects")
    @JsonToString
    var ects: String,

    @JsonName("exame")
    var exame: String,

    @JsonName("inscritos")
    val inscritos: MutableList<Inscrito> = mutableListOf(),

    @JsonName("cursos")
    val cursos: MutableList<Curso> = mutableListOf()
)
