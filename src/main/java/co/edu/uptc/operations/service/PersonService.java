package co.edu.uptc.operations.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;

@Service
public class PersonService {

    @Value("${app.csv.path:/app/data/personas.csv}")
    private String csvFilePath;

    public void streamPersonsToOutput(OutputStream outputStream, String hostname, int page, int size) throws IOException {
        File csvFile = new File(csvFilePath);
        if (!csvFile.exists()) return;

        JsonFactory factory = new JsonFactory();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             JsonGenerator jsonGenerator = factory.createGenerator(outputStream)) {

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("servedBy", hostname);
            jsonGenerator.writeNumberField("page", page);
            jsonGenerator.writeNumberField("size", size);
            jsonGenerator.writeFieldName("data");
            jsonGenerator.writeStartArray();

            String line;
            String[] headers = null;
            long skipRecords = (long) page * size;
            long skippedCount = 0;
            long writtenCount = 0;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");

                if (headers == null) {
                    headers = Arrays.stream(data).map(String::trim).toArray(String[]::new);
                    continue;
                }

                if (skippedCount < skipRecords) {
                    skippedCount++;
                    continue;
                }

                if (writtenCount >= size) {
                    break;
                }

                jsonGenerator.writeStartObject();
                int limit = Math.min(headers.length, data.length);
                for (int i = 0; i < limit; i++) {
                    jsonGenerator.writeStringField(headers[i], data[i].trim());
                }
                jsonGenerator.writeEndObject();

                writtenCount++;
            }

            jsonGenerator.writeEndArray();
            jsonGenerator.writeNumberField("count", writtenCount);
            jsonGenerator.writeEndObject();
            jsonGenerator.flush();
        }
    }
}