package com.converter.fileconverter.controller;

import com.converter.fileconverter.dto.request.CsvDto;
import com.converter.fileconverter.services.JsonToCsvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String convertJsonToCsv(@RequestBody CsvDto request) throws IOException {
        String csvData = csvService.convertJsonToCsv(request.getUsers());
            Files.write(Paths.get("output.csv"), csvData.getBytes());
            return "CSV file created.";
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
    public String convertJsonFileToCsv(
            @RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("outputFile") String outputFile) {
        try {
            csvService.jsonFileToCsvFile(inputFile, outputFile);
            return "JSON to CSV conversion successful. Output file created: " + outputFile;
        } catch (Exception e) {
            return "Error converting JSON to CSV: " + e.getMessage();
        }
    }
}