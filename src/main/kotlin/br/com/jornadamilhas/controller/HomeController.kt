package br.com.jornadamilhas.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
class HomeController() {

    @RequestMapping("/")
    fun redirecionar() : RedirectView {
       return RedirectView("http://localhost:8080/swagger-ui/index.html")
    }
}