package bio.izzo.app.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Test {
    @GetMapping
    public String welcome() {
        return "U're server is already";
    }
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
