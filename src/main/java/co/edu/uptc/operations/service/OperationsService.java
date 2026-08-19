package co.edu.uptc.operations.service;

import co.edu.uptc.operations.dto.OperationResponseDTO;
import co.edu.uptc.operations.exception.DivisionByZeroException;
import co.edu.uptc.operations.exception.InvalidOperationException;
import org.springframework.stereotype.Service;

@Service
public class OperationsService {

    public OperationResponseDTO calculate(double number1, double number2, String operation) {
        String op = operation.toLowerCase().trim();

        double result;
        switch (op) {
            case "sum":
                result = number1 + number2;
                break;

            case "subtract":
                result = number1 - number2;
                break;

            case "multiply":
                result = number1 * number2;
                break;

            case "divide":
                if (number2 == 0) {
                    throw new DivisionByZeroException("Cannot divide by zero");
                }
                result = number1 / number2;
                break;

            default:
                throw new InvalidOperationException("Invalid operation: " + operation);
        }

        return new OperationResponseDTO(number1, number2, operation, result);
    }
}