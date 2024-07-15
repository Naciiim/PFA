package com.example.homepageBackend.service.interfaces;

import com.example.homepageBackend.model.dto.PostingDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExportService {
    void exportToExcel(List<PostingDTO> postings, OutputStream outputStream) throws IOException;
}
