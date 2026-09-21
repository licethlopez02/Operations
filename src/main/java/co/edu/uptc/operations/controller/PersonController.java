package co.edu.uptc.operations.controller;

import co.edu.uptc.operations.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StreamingResponseBody> streamPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        
        String hostname = System.getenv().getOrDefault("HOSTNAME", "container-unknown");
        StreamingResponseBody responseBody = outputStream -> 
                personService.streamPersonsToOutput(outputStream, hostname, page, size);
        
        return ResponseEntity.ok(responseBody);
    }
}