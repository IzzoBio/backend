package bio.izzo.app.backend.service;

import bio.izzo.app.backend.model.entity.Enregistrement;
import bio.izzo.app.backend.repository.EnregistrementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EnregistrementService {
    @Autowired
    private EnregistrementRepository enregistrementRepository;

    public Enregistrement saveEnregistrement(Enregistrement enregistrement) {
        return enregistrementRepository.save(enregistrement);
    }

    public List<Enregistrement> getAllEnregistrements() {
        return enregistrementRepository.findAll();
    }

    public Enregistrement getEnregistrementById(Long id) {
        return enregistrementRepository.findById(id).orElse(null);
    }

    public Enregistrement updateEnregistrement(Long id, Enregistrement updatedEnregistrement) {
        return enregistrementRepository.findById(id).map(enregistrement -> {
            enregistrement.setActuality(updatedEnregistrement.getActuality());
            return enregistrementRepository.save(enregistrement);
        }).orElse(null);
    }
    public void deleteEnregistrement(Long id) {
        enregistrementRepository.deleteById(id);
    }
}
