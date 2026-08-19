package co.edu.uptc.operations.controller;

import co.edu.uptc.operations.dto.OperationResponseDTO;
import co.edu.uptc.operations.service.OperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationsController {

    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<OperationResponseDTO> calculate(
            @RequestParam double number1,
            @RequestParam double number2,
            @RequestParam String operation) {

        OperationResponseDTO response = operationsService.calculate(number1, number2, operation);
        return ResponseEntity.ok(response);
    }
}