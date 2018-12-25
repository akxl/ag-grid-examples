package core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.accept.ContentNegotiationManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.servlet.http.HttpServletResponse

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

// Excel file exporter taken from here: https://aboullaite.me/spring-boot-excel-csv-and-pdf-view-example/
// and here: https://github.com/aboullaite/SpringBoot-Excel-Csv/tree/master/src/main/java/me/aboullaite


@Controller
class ExportController {
    @GetMapping("/download")
    fun download(model: Model): String {
        return ""
    }
}




@Configuration
class WebConfig: WebMvcConfigurerAdapter() {

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        configurer?.defaultContentType(MediaType.APPLICATION_JSON)
                ?.favorPathExtension(true)
    }

    @Bean
    fun contentNegotiatingViewResolver(manager: ContentNegotiationManager): ViewResolver {
        return ContentNegotiatingViewResolver().apply {
            contentNegotiationManager = manager
            viewResolvers = listOf(excelViewResolver())
        }
    }

    @Bean
    fun excelViewResolver() = ExcelViewResolver()

}

class ExcelViewResolver: ViewResolver {
    override fun resolveViewName(viewName: String?, locale: Locale?) = ExcelView()
}
