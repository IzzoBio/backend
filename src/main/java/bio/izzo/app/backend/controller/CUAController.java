package bio.izzo.app.backend.controller;

import bio.izzo.app.backend.model.entity.CUA;
import bio.izzo.app.backend.service.CUAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuas")
public class CUAController {
    @Autowired
    private CUAService cuaService;

    @PostMapping
    public ResponseEntity<CUA> saveCUA(@RequestBody CUA cua) {
        CUA savedCUA = cuaService.saveCUA(cua);
        return new ResponseEntity<>(savedCUA, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CUA>> getAllCUAs() {
        List<CUA> cuas = cuaService.getAllCUAs();
        return new ResponseEntity<>(cuas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CUA> getCUAById(@PathVariable Long id) {
        CUA cua = cuaService.getCUAById(id);
        return new ResponseEntity<>(cua, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CUA> updateCUA(@PathVariable Long id, @RequestBody CUA updatedCUA) {
        CUA cua = cuaService.updateCUA(id, updatedCUA);
        return new ResponseEntity<>(cua, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCUA(@PathVariable Long id) {
        cuaService.deleteCUA(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}