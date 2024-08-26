package bio.izzo.app.backend.controller;

import bio.izzo.app.backend.model.entity.Enregistrement;
import bio.izzo.app.backend.service.EnregistrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enregistrements")
public class EnregistrementController {
    @Autowired
    private EnregistrementService enregistrementService;

    @PostMapping
    public ResponseEntity<Enregistrement> saveEnregistrement(@RequestBody Enregistrement enregistrement) {
        Enregistrement savedEnregistrement = enregistrementService.saveEnregistrement(enregistrement);
        return new ResponseEntity<>(savedEnregistrement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Enregistrement>> getAllEnregistrements() {
        List<Enregistrement> enregistrements = enregistrementService.getAllEnregistrements();
        return new ResponseEntity<>(enregistrements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enregistrement> getEnregistrementById(@PathVariable Long id) {
        Enregistrement enregistrement = enregistrementService.getEnregistrementById(id);
        return new ResponseEntity<>(enregistrement, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enregistrement> updateEnregistrement(@PathVariable Long id, @RequestBody Enregistrement updatedEnregistrement) {
        Enregistrement enregistrement = enregistrementService.updateEnregistrement(id, updatedEnregistrement);
        return new ResponseEntity<>(enregistrement, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnregistrement(@PathVariable Long id) {
        enregistrementService.deleteEnregistrement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
