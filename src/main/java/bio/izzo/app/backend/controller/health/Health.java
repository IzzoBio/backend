package bio.izzo.app.backend.controller.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class Health {
    @GetMapping
    public String welcome() {
        return "U're server is already";
    }
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
