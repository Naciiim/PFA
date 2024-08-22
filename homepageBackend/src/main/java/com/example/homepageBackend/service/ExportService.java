package com.example.homepageBackend.service;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExportService {
    void exportToExcel(List<?> dataList, OutputStream outputStream) throws IOException;
}
