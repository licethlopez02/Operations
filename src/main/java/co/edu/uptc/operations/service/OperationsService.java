package co.edu.uptc.operations.service;

import org.springframework.stereotype.Service;

@Service
public class OperationsService {

    public double calculate(double number1, double number2, String operation) {
        String op = operation.toLowerCase().trim();

        switch (op) {
            case "add":
            case "sum":
            case "+":
                return number1 + number2;

            case "subtract":
            case "minus":
            case "-":
                return number1 - number2;

            case "multiply":
            case "*":
                return number1 * number2;

            case "divide":
            case "/":
                if (number2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                return number1 / number2;

            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }
}