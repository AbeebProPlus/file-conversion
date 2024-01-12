package com.converter.fileconverter.services;

import com.converter.fileconverter.data.User;
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
//    @Override
//    public String jsonFileToCsvFile(MultipartFile inputFile, String outputFile) throws IOException {
//        try (InputStream inputStream = inputFile.getInputStream();
//             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//             Writer writer = new java.io.FileWriter(outputFile)) {
//            char[] buffer = new char[1024];
//            int bytesRead;
//            while ((bytesRead = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, bytesRead);
//            }
//        }
//        return "CSV file created successfully";
//    }
}
