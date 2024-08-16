package bio.izzo.app.backend.controller;

import bio.izzo.app.backend.model.entity.Activitie;
import bio.izzo.app.backend.service.ActivitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivitieController {
    @Autowired
    private ActivitieService activitieService;

    @PostMapping
    public ResponseEntity<Activitie> saveActivitie(@RequestBody Activitie activitie) {
        Activitie savedActivitie = activitieService.saveActivitie(activitie);
        return new ResponseEntity<>(savedActivitie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Activitie>> getAllActivities() {
        List<Activitie> activities = activitieService.getAllActivities();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activitie> getActivitieById(@PathVariable Long id) {
        Activitie activitie = activitieService.getActivitieById(id);
        return new ResponseEntity<>(activitie, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activitie> updateActivitie(@PathVariable Long id, @RequestBody Activitie updatedActivitie) {
        Activitie activitie = activitieService.updateActivitie(id, updatedActivitie);
        return new ResponseEntity<>(activitie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivitie(@PathVariable Long id) {
        activitieService.deleteActivitie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
