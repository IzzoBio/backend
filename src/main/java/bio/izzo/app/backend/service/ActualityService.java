package bio.izzo.app.backend.service;

import bio.izzo.app.backend.model.entity.Actuality;
import bio.izzo.app.backend.repository.ActualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActualityService {
    @Autowired
    private ActualityRepository actualityRepository;

    public Actuality saveActuality(Actuality actuality) {
        return actualityRepository.save(actuality);
    }

    public List<Actuality> getAllActualities() {
        return actualityRepository.findAll();
    }

    public Actuality getActualityById(Long id) {
        return actualityRepository.findById(id).orElse(null);
    }

    public Actuality updateActuality(Long id, Actuality updatedActuality) {
        return actualityRepository.findById(id).map(actuality -> {
            actuality.setDescription(updatedActuality.getDescription());
            actuality.setImage(updatedActuality.getImage());
            actuality.setContenu(updatedActuality.getContenu());
            return actualityRepository.save(actuality);
        }).orElse(null);
    }

    public void deleteActuality(Long id) {
        actualityRepository.deleteById(id);
    }
}
