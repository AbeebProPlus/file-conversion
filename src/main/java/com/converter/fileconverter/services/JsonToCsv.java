package com.converter.fileconverter.services;

import com.converter.fileconverter.data.User;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface JsonToCsv {
    String jsonFileToCsvFile(MultipartFile inputFile, String outputPath, String outputFileName) throws IOException;
}
