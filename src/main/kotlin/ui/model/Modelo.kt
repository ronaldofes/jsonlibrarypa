package ui.model

data class Modelo(
    var uc: String,
    var ects: String,
    var exame: String,
    val inscritos: MutableList<Inscrito> = mutableListOf(),
    val cursos: MutableList<Curso> = mutableListOf()
)