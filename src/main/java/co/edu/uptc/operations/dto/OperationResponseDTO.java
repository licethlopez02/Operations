package co.edu.uptc.operations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperationResponseDTO {
    private final double number1;
    private final double number2;
    private final String operation;
    private final double result;    
}
