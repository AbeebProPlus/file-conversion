package com.converter.fileconverter.services;

import com.converter.fileconverter.data.JsonData;
import com.converter.fileconverter.data.User;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonToCsvService implements JsonToCsv {
    public String convertJsonToCsv(List<User> users) {
        return users.stream()
                .map(user -> String.format("%s,%s,%s%n", user.getName(), user.getEmail(), user.getBalance()))
                .collect(Collectors.joining());
    }

    @Override
    public String readFromCsvWriteToCSV(MultipartFile inputFile, String outputFile) {
        try (InputStream inputStream = inputFile.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            return "CSV file created successfully";
        } catch (IOException e) {
            throw new RuntimeException("Error processing CSV files", e);
        }
    }
    @Override
    public String jsonFileToCsvFile(MultipartFile inputFile, String outputFile) throws IOException {
        try (InputStream inputStream = inputFile.getInputStream();
             Writer writer = new java.io.FileWriter(outputFile)) {
            ObjectMapper objectMapper = new ObjectMapper();
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.schemaFor(JsonData.class).withHeader();

            JsonData[] jsonDataArray = objectMapper.readValue(inputStream, JsonData[].class);

            for (JsonData jsonData : jsonDataArray) {
                String csvLine = csvMapper.writer(csvSchema).writeValueAsString(jsonData);
                writer.write(csvLine + "\n");
            }

            return "CSV file created successfully";
        }
    }
}
