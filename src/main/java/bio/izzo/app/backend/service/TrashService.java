package bio.izzo.app.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bio.izzo.app.backend.model.entity.Trash;
import bio.izzo.app.backend.repository.TrashRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrashService {
  @Autowired
  private TrashRepository trashRepository;

  public List<Trash> getAllTrash() {
    return trashRepository.findAll();
  }

  public Optional<Trash> getTrashById(Long id) {
    return trashRepository.findById(id);
  }

  public Trash saveTrash(Trash trash) {
    return trashRepository.save(trash);
  }

  public void deleteTrash(Long id) {
    trashRepository.deleteById(id);
  }

  public Trash updateTrash(Long id, Trash updatedTrash) {
    return trashRepository.findById(id).map(trash -> {
      trash.setLat(updatedTrash.getLat());
      trash.setLon(updatedTrash.getLon());
      trash.setType(updatedTrash.getType());
      return trashRepository.save(trash);
    }).orElseGet(null);
  }
}
