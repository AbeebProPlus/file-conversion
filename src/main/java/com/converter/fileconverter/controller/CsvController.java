package com.converter.fileconverter.controller;

import com.converter.fileconverter.dto.request.CsvDto;
import com.converter.fileconverter.services.JsonToCsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CsvController {

    private final JsonToCsvService csvService;

    @PostMapping("/json-to-csv")
    public ResponseEntity<String> convertJsonToCsv(@RequestBody CsvDto request) throws IOException {
        String outputPath = request.getOutputPath();
        String outputFileName = request.getOutputFileName() != null ? request.getOutputFileName() : "output.csv";

        String csvData = csvService.convertJsonToCsv(request.getUsers(), outputPath, outputFileName);
        if (outputPath == null || outputPath.trim().isEmpty()) {
            Files.write(Paths.get(outputFileName), csvData.getBytes());
            return ResponseEntity.ok("CSV file created at: " + Paths.get(outputFileName).toAbsolutePath());
        } else {
            Files.write(Paths.get(outputPath, outputFileName), csvData.getBytes());
            return ResponseEntity.ok("CSV file created at: " + Paths.get(outputPath, outputFileName).toAbsolutePath());
        }
    }
    @PostMapping("/convert-csv")
    public String convertCsvFile(
            @RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("outputFile") String outputFile) {
        try {
            csvService.readFromCsvWriteToCSV(inputFile, outputFile);
            return "CSV conversion successful. Output file created: " + outputFile;
        } catch (Exception e) {
            return "Error converting CSV: " + e.getMessage();
        }
    }
    @PostMapping("/convert-json-to-csv")
    public String convertJsonFileToCsvFile(
            @RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("outputPath") String outputPath,
            @RequestParam("outputFileName") String outputFileName) {
        try {
            csvService.jsonFileToCsvFile(inputFile, outputPath, outputFileName);
            return "JSON to CSV conversion successful.";
        } catch (Exception e) {
            return "Error converting JSON to CSV: " + e.getMessage();
        }
    }
}