package com.example.homepageBackend.service.implementation;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.service.interfaces.ExportService;
import com.example.homepageBackend.util.DatabaseUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Postings");

            // Récupérer les noms de colonnes depuis la base de données à l'aide de la classe utilitaire
            List<String> columnNames = DatabaseUtils.getDatabaseColumnNames("POSTING");

            // Créer la ligne d'en-tête dynamiquement
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                headerRow.createCell(i).setCellValue(columnName);
            }

            // Créer les lignes de données
            int rowIdx = 1;
            for (PostingDTO posting : postings) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    Method getter = findGetterMethod(posting, convertColumnNameToFieldName(columnName));

                    if (getter != null) {
                        try {
                            Object value = getter.invoke(posting);
                            if (value != null) {
                                if (value instanceof Date) {
                                    row.createCell(i).setCellValue((Date) value);
                                } else {
                                    row.createCell(i).setCellValue(value.toString());
                                }
                            } else {
                                row.createCell(i).setCellValue("");
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            row.createCell(i).setCellValue("Erreur de récupération");
                        }
                    } else {
                        row.createCell(i).setCellValue("Méthode getter non trouvée pour " + columnName);
                        // Ajoutez des logs pour voir quels noms de colonnes posent problème
                        System.out.println("Méthode getter non trouvée pour la colonne : " + columnName);
                    }
                }
            }

            // Écrire le workbook dans le outputStream
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Propager l'exception si elle se produit lors de l'écriture dans le flux de sortie
        }
    }

    // Méthode pour trouver la méthode getter correspondante pour une propriété donnée
    private Method findGetterMethod(Object object, String fieldName) {
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(getterMethodName)) {
                return method;
            }
        }
        return null;
    }

    // Convertit le nom de colonne en nom de champ Java
    private String convertColumnNameToFieldName(String columnName) {
        StringBuilder fieldNameBuilder = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    fieldNameBuilder.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    fieldNameBuilder.append(Character.toLowerCase(c));
                }
            }
        }
        return fieldNameBuilder.toString();
    }

}
