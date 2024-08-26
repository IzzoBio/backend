package bio.izzo.app.backend.controller;

import bio.izzo.app.backend.model.entity.Actuality;
import bio.izzo.app.backend.service.ActualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actualities")
public class ActualityController {
    @Autowired
    private ActualityService actualityService;

    @PostMapping
    public ResponseEntity<Actuality> saveActuality(@RequestBody Actuality actuality) {
        Actuality savedActuality = actualityService.saveActuality(actuality);
        return new ResponseEntity<>(savedActuality, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Actuality>> getAllActualities() {
        List<Actuality> actualities = actualityService.getAllActualities();
        return new ResponseEntity<>(actualities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actuality> getActualityById(@PathVariable Long id) {
        Actuality actuality = actualityService.getActualityById(id);
        return new ResponseEntity<>(actuality, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actuality> updateActuality(@PathVariable Long id, @RequestBody Actuality updatedActuality) {
        Actuality actuality = actualityService.updateActuality(id, updatedActuality);
        return new ResponseEntity<>(actuality, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActuality(@PathVariable Long id) {
        actualityService.deleteActuality(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
