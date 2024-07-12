package com.example.homepageBackend.service.interfaces;

import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.repository.PostingRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExportService {

//    @Autowired
//    private PostingRepository postingRepository;
//
//
//
//    public byte[] exportPostings(String transactionId) throws IOException {
//        Posting posting = postingRepository.findPostingByTransactionId(transactionId);
//        if (posting != null) {
//            return createExcelFile(posting, "Postings");
//        } else {
//            throw new RuntimeException("Aucune entité Posting trouvée pour transactionId : " + transactionId);
//        }
//    }
//
//
//
//
//
//    private void addDataToWorkbook(Workbook workbook, Object data, String sheetName) {
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        // en-tête
//        Row headerRow = sheet.createRow(0);
//        if (data instanceof Posting) {
//            String[] headers = { "Transaction ID","Transaction date","Debit account", "Credit account","Status"};
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//            }
//        }
//
//
//        // les données
//        Row dataRow = sheet.createRow(1);
//        if (data instanceof Posting) {
//            Posting posting = (Posting) data;
//            dataRow.createCell(0).setCellValue(posting.getTransactionId());
//            dataRow.createCell(1).setCellValue(posting.getRef());
//            dataRow.createCell(2).setCellValue(posting.getEvent());
//            dataRow.createCell(3).setCellValue(posting.getTransactionDate());
//            dataRow.createCell(4).setCellValue(posting.getDebitAccount());
//            dataRow.createCell(5).setCellValue(posting.getCreditAccount());
//            dataRow.createCell(6).setCellValue(posting.getStatus());
//        }
//    }
//
//
//    private byte[] createExcelFile(Object data, String sheetName) throws IOException {
//        Workbook workbook = new XSSFWorkbook();
//        addDataToWorkbook(workbook, data, sheetName);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        return out.toByteArray();
//    }
}
