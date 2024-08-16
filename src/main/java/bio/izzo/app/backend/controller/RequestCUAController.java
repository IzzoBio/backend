package bio.izzo.app.backend.controller;

import bio.izzo.app.backend.model.entity.RequestCUA;
import bio.izzo.app.backend.service.RequestCUAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/requestCUAs")
public class RequestCUAController {
    @Autowired
    private RequestCUAService requestCUAService;

    @PostMapping
    public ResponseEntity<RequestCUA> saveRequestCUA(@RequestBody RequestCUA requestCUA) {
        RequestCUA savedRequestCUA = requestCUAService.saveRequestCUA(requestCUA);
        return new ResponseEntity<>(savedRequestCUA, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RequestCUA>> getAllRequestCUAs() {
        List<RequestCUA> requestCUAs = requestCUAService.getAllRequestCUAs();
        return new ResponseEntity<>(requestCUAs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestCUA> getRequestCUAById(@PathVariable Long id) {
        RequestCUA requestCUA = requestCUAService.getRequestCUAById(id);
        return new ResponseEntity<>(requestCUA, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestCUA> updateRequestCUA(@PathVariable Long id, @RequestBody RequestCUA updatedRequestCUA) {
        RequestCUA requestCUA = requestCUAService.updateRequestCUA(id, updatedRequestCUA);
        return new ResponseEntity<>(requestCUA, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestCUA(@PathVariable Long id) {
        requestCUAService.deleteRequestCUA(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
