package ui.model

import annotation.JsonName
import annotation.JsonToString

data class Modelo(
    @JsonName("uc_modelo")
    var uc: String,

    @JsonName("ects_modelo")
    @JsonToString
    var ects: String,

    @JsonName("exame_modelo")
    var exame: String,

    @JsonName("inscritos")
    val inscritos: MutableList<Inscrito> = mutableListOf(),

    @JsonName("cursos")
    val cursos: MutableList<Curso> = mutableListOf()
)
