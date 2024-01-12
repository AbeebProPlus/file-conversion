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