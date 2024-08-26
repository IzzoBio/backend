package bio.izzo.app.backend.service;

import bio.izzo.app.backend.model.entity.CUA;
import bio.izzo.app.backend.repository.CUARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CUAService {
    @Autowired
    private CUARepository cuaRepository;

    public CUA saveCUA(CUA cua) {
        return cuaRepository.save(cua);
    }

    public List<CUA> getAllCUAs() {
        return cuaRepository.findAll();
    }

    public CUA getCUAById(Long id) {
        return cuaRepository.findById(id).orElse(null);
    }

    public CUA updateCUA(Long id, CUA updatedCUA) {
        return cuaRepository.findById(id).map(cua -> {
            cua.setCalendrier(updatedCUA.getCalendrier());
            return cuaRepository.save(cua);
        }).orElse(null);
    }

    public void deleteCUA(Long id) {
        cuaRepository.deleteById(id);
    }
}
