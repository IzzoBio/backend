package bio.izzo.app.backend.service;

import bio.izzo.app.backend.model.entity.RequestCUA;
import bio.izzo.app.backend.repository.RequestCUARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequestCUAService {
    @Autowired
    private RequestCUARepository requestCUARepository;

    public RequestCUA saveRequestCUA(RequestCUA requestCUA) {
        return requestCUARepository.save(requestCUA);
    }

    public List<RequestCUA> getAllRequestCUAs() {
        return requestCUARepository.findAll();
    }

    public RequestCUA getRequestCUAById(Long id) {
        return requestCUARepository.findById(id).orElse(null);
    }

    public RequestCUA updateRequestCUA(Long id, RequestCUA updatedRequestCUA) {
        return requestCUARepository.findById(id).map(requestCUA -> {
            requestCUA.setType(updatedRequestCUA.getType());
            requestCUA.setDescription(updatedRequestCUA.getDescription());
            return requestCUARepository.save(requestCUA);
        }).orElse(null);
    }

    public void deleteRequestCUA(Long id) {
        requestCUARepository.deleteById(id);
    }
}
