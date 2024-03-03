package com.example.demo.conroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;

@RestController
public class FileUploadController {
    
    @Autowired
    private FileService fileService;
    
    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("startRow") int startRow) {
        try {
            return ResponseEntity.ok(fileService.processExcel(file, startRow));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error processing Excel file: " + e.getMessage());
        }
    }

    @PostMapping("/uploadXml")
    public ResponseEntity<?> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                           @RequestParam("outputLocation") String outputLocation) {
        try {
            fileService.convertXmlToJson(file, outputLocation);
            return ResponseEntity.ok("XML file processed and JSON generated at " + outputLocation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error processing XML file: " + e.getMessage());
        }
    }
}
