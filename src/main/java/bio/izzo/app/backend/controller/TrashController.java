package bio.izzo.app.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bio.izzo.app.backend.model.entity.Trash;
import bio.izzo.app.backend.service.TrashService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trash")
public class TrashController {
  @Autowired
  private TrashService trashService;

  @GetMapping
  public List<Trash> getAllTrash() {
    return trashService.getAllTrash();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Trash> getTrashById(@PathVariable Long id) {
    Optional<Trash> trash = trashService.getTrashById(id);
    return trash.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<Trash> createTrash(@RequestBody Trash trash) {
    Trash savedTrash = trashService.saveTrash(trash);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedTrash);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Trash> updateTrash(@PathVariable Long id, @RequestBody Trash trash) {
    Trash updatedTrash = trashService.updateTrash(id, trash);
    return ResponseEntity.ok(updatedTrash);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrash(@PathVariable Long id) {
    trashService.deleteTrash(id);
    return ResponseEntity.noContent().build();
  }
}
