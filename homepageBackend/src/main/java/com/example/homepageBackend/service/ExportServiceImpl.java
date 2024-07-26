package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.util.DatabaseUtils;
import com.example.homepageBackend.util.Utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    @Override
    public void exportToExcel(List<PostingDTO> postings, OutputStream outputStream) throws IOException {
        if (postings == null || postings.isEmpty()) {
            throw new IllegalArgumentException("La liste des postings est null ou vide");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Postings");

            // Récupérer les noms de colonnes depuis le premier objet PostingDTO
            List<String> columnNames = DatabaseUtils.getDatabaseColumnNames("POSTING");

            if (columnNames == null || columnNames.isEmpty()) {
                throw new IllegalStateException("La liste des noms de colonnes est null ou vide");
            }

            // Créer la ligne d'en-tête dynamiquement
            Row headerRow = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);

            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnName);
                cell.setCellStyle(headerCellStyle);
            }

            // Créer les styles pour les dates
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

            // Créer les lignes de données
            int rowIdx = 1;
            for (PostingDTO posting : postings) {
                if (posting == null) {
                    continue; // Ignorer les éléments null dans postings
                }
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    Method getter = Utils.findGetterMethod(posting, Utils.convertColumnNameToFieldName(columnName));

                    if (getter != null) {
                        try {
                            Object value = getter.invoke(posting);
                            Cell cell = row.createCell(i);
                            if (value != null) {
                                if (value instanceof Date) {
                                    cell.setCellValue((Date) value);
                                    cell.setCellStyle(dateCellStyle);
                                } else if (value instanceof Number) {
                                    cell.setCellValue(((Number) value).doubleValue());
                                } else {
                                    cell.setCellValue(value.toString());
                                }
                            } else {
                                cell.setCellValue("");
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            row.createCell(i).setCellValue("Erreur de récupération");
                        }
                    } else {
                        row.createCell(i).setCellValue("Méthode getter non trouvée pour " + columnName);
                        System.out.println("Méthode getter non trouvée pour la colonne : " + columnName);
                    }
                }
            }

            // Ajuster automatiquement la largeur des colonnes
            for (int i = 0; i < columnNames.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Écrire le workbook dans le outputStream
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Propager l'exception si elle se produit lors de l'écriture dans le flux de sortie
        }
    }
}