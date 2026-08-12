package co.edu.uptc.operations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.operations.service.OperationsService;

@RestController
@RequestMapping("/api/v1/operations")
public class OperationsController {

    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    @GetMapping("/calculate")
    public double calculate(
            @RequestParam double number1,
            @RequestParam double number2,
            @RequestParam String operation) {

        return operationsService.calculate(number1, number2, operation);
    }
}