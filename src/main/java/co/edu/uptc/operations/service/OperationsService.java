package co.edu.uptc.operations.service;

import org.springframework.stereotype.Service;

@Service
public class OperationsService {

    public double calculate(double number1, double number2, String operation) {
        String op = operation.toLowerCase().trim();

        switch (op) {
            case "sum":
                return number1 + number2;

            case "subtract":
                return number1 - number2;

            case "multiply":
                return number1 * number2;

            case "divide":
                if (number2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                return number1 / number2;

            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }
}