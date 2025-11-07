package com.ilya.practicenavigation.data

data class Student(
    val id: Long,
    val name: String,
    val description: String,
)

var students = listOf<Student>(
    Student(1L, "Emilia Hartcourt", "Ex agente de Argus"),
    Student(2L, "PeaceMaker", "El Héroe de la paz"),
    Student(3L, "Economos", "El amigo cobarde que resulta ser el más valiente"),
    Student(4L, "Eagly", "El Pato favorito de todo el mundo"),
    Student(5L, "Vigilanti", "El psicopata en el que más puedes confiar"),
    Student(6L, "Adebayo", "Hemana sin lazos sanguineos de Peacemaker"),
)