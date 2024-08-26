package bio.izzo.app.backend.service;

import bio.izzo.app.backend.model.entity.Activitie;
import bio.izzo.app.backend.repository.ActivitieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivitieService {
    @Autowired
    private ActivitieRepository activitieRepository;

    public Activitie saveActivitie(Activitie activitie) {
        return activitieRepository.save(activitie);
    }

    public List<Activitie> getAllActivities() {
        return activitieRepository.findAll();
    }

    public Activitie getActivitieById(Long id) {
        return activitieRepository.findById(id).orElse(null);
    }

    public Activitie updateActivitie(Long id, Activitie updatedActivite) {
        return activitieRepository.findById(id).map(activitie -> {
            activitie.setNombreDePoint(updatedActivite.getNombreDePoint());
            return activitieRepository.save(activitie);
        }).orElse(null);
    }

    public void deleteActivitie(Long id) {
        activitieRepository.deleteById(id);
    }
}
