package com.example.demo.service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@Service
public class FileService {

    public List<List<String>> processExcel(MultipartFile file, int startRow) throws IOException {
        List<List<String>> data = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        
        for (int i = startRow - 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                List<String> rowData = new ArrayList<>();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case BLANK:
                            rowData.add("");
                            break;
                        default:
                            rowData.add("");
                    }
                }
                data.add(rowData);
            }
        }
        workbook.close();
        return data;
    }
    public void convertXmlToJson(MultipartFile file, String outputLocation) throws Exception {
        File outputFile = new File(outputLocation);

       
        JAXBContext context = JAXBContext.newInstance(XmlData.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

      
        XmlData xmlData = (XmlData) unmarshaller.unmarshal(file.getInputStream());

      
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputFile, xmlData);
    }
}


