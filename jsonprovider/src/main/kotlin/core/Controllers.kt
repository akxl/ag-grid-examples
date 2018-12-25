package core

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

data class Greeting(val id: Long, val content: String)

@RestController
class GreetingController {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")

}


data class Student(val id: Long, val name: String, val averageScore: Double)

@RestController
class StudentController {

    @GetMapping("/api/students")
    fun getAllStudents(@RequestParam(value = "name", required = false) name: String?) = allStudents
            .filter { if (name.isNullOrBlank()) true else it.name.contains(Regex("\\b$name\\b")) }
            .sortedBy { it.id }

}

private val allStudents = listOf(
            Student(1, "John Richardson", 53.6),
            Student(2, "Emily Smith", 89.6),
            Student(3, "Thomas Boyle", 30.5),
            Student(4, "Adam Johnson", 53.3),
            Student(5, "Victoria Cooper", 78.0)
        )