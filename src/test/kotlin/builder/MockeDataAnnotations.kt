package builder

import annotation.*

data class Student(
    @JsonName("numero") val id: Int,
    @JsonName("nome") val name: String,
    @JsonName("internacional") val isInternational: Boolean
)

data class Course(
    @JsonName("uc") val name: String,
    @JsonName("ects") val ects: Float,
    @JsonName("data-exame") @JsonExclude val examDate: Any? = null,
    @JsonName("inscritos") val students: List<Student>
)

