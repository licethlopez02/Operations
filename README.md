# Operations

Servicio web REST para ejecutar operaciones matemáticas básicas. Está desarrollado con Java 17 y Spring Boot, siguiendo una separación por capas entre controlador, servicio, DTO y manejo de excepciones.

El proyecto centraliza el tratamiento de errores mediante `@RestControllerAdvice`, lo que permite entregar respuestas consistentes a los clientes y facilita su integración en sistemas distribuidos.

## Requisitos

- Java 17 o superior.
- Maven Wrapper incluido en el proyecto (`mvnw` o `mvnw.cmd`).

No es necesario instalar Maven de forma global.

## Ejecución

Desde la raíz del proyecto:

### Windows

```powershell
./mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

La aplicación queda disponible en:

```text
http://localhost:8081
```

También se puede generar el archivo ejecutable y levantarlo:

```bash
./mvnw clean package
java -jar target/Operations-0.0.1-SNAPSHOT.jar
```

En Windows, use `mvnw.cmd` en lugar de `./mvnw`.

## API

### Calcular una operación

```http
GET /api/v1/operations/calculate
```

#### Parámetros de consulta

| Parámetro | Tipo | Descripción |
|---|---|---|
| `number1` | `double` | Primer número de la operación. |
| `number2` | `double` | Segundo número de la operación. |
| `operation` | `string` | Operación a ejecutar. |

Operaciones soportadas:

- `sum`: suma.
- `subtract`: resta.
- `multiply`: multiplicación.
- `divide`: división.

Los espacios al inicio y al final de `operation` se ignoran y el valor no distingue entre mayúsculas y minúsculas.

#### Ejemplo de solicitud

```bash
curl "http://localhost:8081/api/v1/operations/calculate?number1=10&number2=2&operation=divide"
```

#### Respuesta exitosa: `200 OK`

```json
{
  "number1": 10.0,
  "number2": 2.0,
  "operation": "divide",
  "result": 5.0
}
```

## Manejo centralizado de errores

Todas las excepciones se procesan en `GlobalExceptionHandler`. La respuesta HTTP que recibe el cliente utiliza el siguiente formato:

```json
{
  "timestamp": "2026-08-19T10:15:30.123",
  "status": 400,
  "error": "Bad Request",
  "message": "Cannot divide by zero"
}
```

Casos contemplados:

| Situación | Estado | Mensaje |
|---|---:|---|
| División entre cero | `400` | `Cannot divide by zero` |
| Operación no soportada | `400` | `Invalid operation: ...` |
| Parámetro numérico inválido | `400` | Indica el valor y el parámetro inválido. |
| Error no contemplado | `500` | `An unexpected error occurred` |

Ejemplo de error por operación inválida:

```bash
curl "http://localhost:8081/api/v1/operations/calculate?number1=10&number2=2&operation=power"
```

El formato anterior corresponde al cuerpo de la respuesta HTTP, no al archivo de logs. Por ejemplo, una división entre cero devuelve `400 Bad Request` con un objeto `ErrorResponseDTO` similar a este:

```json
{
  "timestamp": "2026-08-19T11:00:59.309",
  "status": 400,
  "error": "Bad Request",
  "message": "Cannot divide by zero"
}
```

## Arquitectura

El código se organiza en las siguientes capas:

- `controller`: expone los endpoints HTTP.
- `service`: contiene la lógica de cálculo.
- `dto`: define los contratos de respuesta.
- `exception`: contiene las excepciones de dominio y el manejador global.

## Logs

- Los logs se muestran en consola en formato legible para desarrollo.
- Los eventos con nivel `ERROR` se almacenan en `logs/error.json` en formato JSON estructurado mediante `LogstashEncoder`.
- El archivo contiene eventos de Logback, por lo que sus campos (`@timestamp`, `message`, `logger_name`, `level`, entre otros) son diferentes al contrato HTTP de `ErrorResponseDTO`.
- Por ejemplo, `message` contiene el texto escrito por `log.error(...)`, mientras que `status` y `error` pertenecen a la respuesta HTTP y no se agregan automáticamente al evento de log.
- El archivo rota diariamente y conserva un máximo de ocho días.

## Tecnologías

- Java 17.
- Spring Boot 4.1.0.
- Spring Web MVC.
- Lombok.
- Logback y Logstash Logback Encoder.
- Maven Wrapper.