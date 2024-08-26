package bio.izzo.app.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.izzo.app.backend.model.entity.Suggestion;
import bio.izzo.app.backend.repository.SuggestionRepository;

@Service
public class SuggestionService {
  @Autowired
  private SuggestionRepository suggestionRepository;

  public List<Suggestion> getAllSuggestion() {
    return suggestionRepository.findAll();
  }

  public Optional<Suggestion> getSuggestionById(Long id) {
    return suggestionRepository.findById(id);
  }

  public Suggestion saveSuggestion(Suggestion suggestion) {
    return suggestionRepository.save(suggestion);
  }

  public void deleteSuggestion(Long id) {
    suggestionRepository.deleteById(id);
  }

  public Suggestion updateSuggestion(Long id, Suggestion updatedSuggestion) {
    return suggestionRepository.findById(id).map(suggestion -> {
      suggestion.setContent(updatedSuggestion.getContent());
      return suggestionRepository.save(suggestion);
    }).orElseGet(null);
  }
}
