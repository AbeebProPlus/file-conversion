package com.converter.fileconverter.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JsonToCsvService implements JsonToCsv {

    @Override
    public String jsonFileToCsvFile(MultipartFile inputFile, String outputPath, String outputFileName) {
        String defaultPath = System.getProperty("user.dir");
        outputPath = (outputPath == null || outputPath.trim().isEmpty()) ? defaultPath : outputPath;

        File outputFile = new File(outputPath, outputFileName);

        try (InputStream inputStream = inputFile.getInputStream();
             Writer writer = new FileWriter(outputFile)) {

            ObjectMapper objectMapper = new ObjectMapper();
//            CsvMapper csvMapper = new CsvMapper();

            JsonNode jsonNode = objectMapper.readTree(inputStream);
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                JsonNode firstObject = jsonNode.get(0);

                String header = createCsvHeader(firstObject.fieldNames());
                writer.write(header);

                processJsonArray(jsonNode, writer);

                return "CSV file created successfully at: " + outputFile.getAbsolutePath();
            } else {
                return "Invalid JSON format or empty array.";
            }
        } catch (IOException e) {
            return "Error creating CSV file: " + e.getMessage();
        }
    }

    private String createCsvHeader(Iterator<String> fieldNamesIterator) {
        Spliterator<String> fieldNamesSpliterator = Spliterators.spliteratorUnknownSize(fieldNamesIterator, 0);
        return StreamSupport.stream(fieldNamesSpliterator, false)
                .collect(Collectors.joining(",")) + "\n";
    }

    private void processJsonArray(JsonNode jsonNode, Writer writer) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        for (JsonNode jsonData : jsonNode) {
            writeJsonDataValues(jsonData.elements(), writer);
        }
    }

    private void writeJsonDataValues(Iterator<JsonNode> valuesIterator, Writer writer) throws IOException {
        StringJoiner csvLineJoiner = new StringJoiner(",");
        valuesIterator.forEachRemaining(value -> csvLineJoiner.add(value.asText()));
        String csvLine = csvLineJoiner + "\n";
        writer.write(csvLine);
    }
}
