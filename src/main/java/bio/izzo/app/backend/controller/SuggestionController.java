package bio.izzo.app.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bio.izzo.app.backend.model.entity.Suggestion;
import bio.izzo.app.backend.service.SuggestionService;

@RestController
@RequestMapping("/api/suggestion")
public class SuggestionController {
  @Autowired
  private SuggestionService suggestionService;

  @GetMapping
  public List<Suggestion> getAllSuggestion() {
    return suggestionService.getAllSuggestion();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Suggestion> getSuggestionById(@PathVariable Long id) {
    Optional<Suggestion> suggestion = suggestionService.getSuggestionById(id);
    return suggestion.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<Suggestion> createSuggestion(@RequestBody Suggestion suggestion) {
    Suggestion savedSuggestion = suggestionService.saveSuggestion(suggestion);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedSuggestion);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Suggestion> updateSuggestion(@PathVariable Long id, @RequestBody Suggestion suggestion) {
    Suggestion updatedSuggestion = suggestionService.updateSuggestion(id, suggestion);
    return ResponseEntity.ok(updatedSuggestion);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSuggestion(@PathVariable Long id) {
    suggestionService.deleteSuggestion(id);
    return ResponseEntity.noContent().build();
  }
}
