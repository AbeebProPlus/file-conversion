package com.converter.fileconverter.services;

import com.converter.fileconverter.data.User;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface JsonToCsv {
    String convertJsonToCsv(List<User> users);
    String readFromCsvWriteToCSV(MultipartFile inputFile, String outputFile);
    String jsonFileToCsvFile(MultipartFile inputFile, String outputFile) throws IOException;
}
