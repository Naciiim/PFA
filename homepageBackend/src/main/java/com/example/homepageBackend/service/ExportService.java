package com.example.homepageBackend.service;

import com.example.homepageBackend.model.entity.Cre;
import com.example.homepageBackend.model.entity.Mouvement;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.repository.CreRepository;
import com.example.homepageBackend.repository.MouvementRepository;
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

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private CreRepository creRepository;

    public byte[] exportPostings(String transactionId) throws IOException {
        Posting posting = postingRepository.findPostingByTransactionId(transactionId);
        if (posting != null) {
            return createExcelFile(posting, "Postings");
        } else {
            throw new RuntimeException("Aucune entité Posting trouvée pour transactionId : " + transactionId);
        }
    }

    public byte[] exportMouvements(String transactionId) throws IOException {
        Mouvement mouvement = mouvementRepository.findMouvementByTransactionId(transactionId);
        if (mouvement != null) {
            return createExcelFile(mouvement, "Mouvements");
        } else {
            throw new RuntimeException("Aucune entité Mouvement trouvée pour transactionId : " + transactionId);
        }
    }

    public byte[] exportCres(String transactionId) throws IOException {
        Cre cre = creRepository.findCreByTransactionId(transactionId);
        if (cre != null) {
            return createExcelFile(cre, "Cres");
        } else {
            throw new RuntimeException("Aucune entité Cre trouvée pour transactionId : " + transactionId);
        }
    }

    public byte[] exportAll(String transactionId) throws IOException {
        Posting posting = postingRepository.findPostingByTransactionId(transactionId);
        Mouvement mouvement = mouvementRepository.findMouvementByTransactionId(transactionId);
        Cre cre = creRepository.findCreByTransactionId(transactionId);

        Workbook workbook = new XSSFWorkbook();

        if (posting != null) {
            addDataToWorkbook(workbook, posting, "Postings");
        }

        if (mouvement != null) {
            addDataToWorkbook(workbook, mouvement, "Mouvements");
        }

        if (cre != null) {
            addDataToWorkbook(workbook, cre, "Cres");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();
    }

    private void addDataToWorkbook(Workbook workbook, Object data, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);

        // en-tête
        Row headerRow = sheet.createRow(0);
        if (data instanceof Posting) {
            String[] headers = { "Transaction ID","Transaction date","Debit account", "Credit account","Status"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        } else if (data instanceof Mouvement) {
            String[] headers = {"Transaction ID", "Ref","Montant","Date","Event","Status"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        } else if (data instanceof Cre) {
            String[] headers = {"Payment ID", "Transaction ID","Method","Date","Event","Status"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        }

        // les données
        Row dataRow = sheet.createRow(1);
        if (data instanceof Posting) {
            Posting posting = (Posting) data;
            dataRow.createCell(0).setCellValue(posting.getTransactionId());
            dataRow.createCell(1).setCellValue(posting.getTransactionDate());
            dataRow.createCell(2).setCellValue(posting.getDebitAccount());
            dataRow.createCell(3).setCellValue(posting.getCreditAccount());
            dataRow.createCell(4).setCellValue(posting.getStatus());
        } else if (data instanceof Mouvement) {
            Mouvement mouvement = (Mouvement) data;
            dataRow.createCell(0).setCellValue(mouvement.getTransactionId());
            dataRow.createCell(1).setCellValue(mouvement.getRef());
            dataRow.createCell(2).setCellValue(mouvement.getAmount());
            dataRow.createCell(3).setCellValue(mouvement.getDate());
            dataRow.createCell(4).setCellValue(mouvement.getEvent());
            dataRow.createCell(5).setCellValue(mouvement.getStatus());

        } else if (data instanceof Cre) {
            Cre cre = (Cre) data;
            dataRow.createCell(0).setCellValue(cre.getPaymentId());
            dataRow.createCell(1).setCellValue(cre.getTransactionId());
            dataRow.createCell(2).setCellValue(cre.getMethod());
            dataRow.createCell(3).setCellValue(cre.getDate());
            dataRow.createCell(4).setCellValue(cre.getEvent());
            dataRow.createCell(5).setCellValue(cre.getStatus());
        }
    }


    private byte[] createExcelFile(Object data, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        addDataToWorkbook(workbook, data, sheetName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();
    }
}
