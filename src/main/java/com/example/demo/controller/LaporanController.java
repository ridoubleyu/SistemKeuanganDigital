package com.example.demo.controller;

import com.example.demo.model.Transaksi;
import com.example.demo.model.User;
import com.example.demo.repository.TransaksiRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Controller
public class LaporanController {

    @Autowired
    private TransaksiRepository transaksiRepository;
    @GetMapping("/laporan")
    public String laporan(
            Model model,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute(
                "listTransaksi",
                transaksiRepository.findByUser(user)
        );

        return "laporan";
    }

@GetMapping("/laporan/export")
public void exportExcel(
        HttpSession session,
        HttpServletResponse response
) throws IOException {

    User user = (User) session.getAttribute("user");

    if(user == null){
        response.sendRedirect("/login");
        return;
    }

    List<Transaksi> listTransaksi =
            transaksiRepository.findByUser(user);

    Workbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("Laporan");

    Row header = sheet.createRow(0);

    header.createCell(0).setCellValue("Tanggal");
    header.createCell(1).setCellValue("Nama");
    header.createCell(2).setCellValue("Kategori");
    header.createCell(3).setCellValue("Jenis");
    header.createCell(4).setCellValue("Jumlah");

    int rowNum = 1;

    for(Transaksi t : listTransaksi){

        Row row = sheet.createRow(rowNum++);

        row.createCell(0)
                .setCellValue(
                        t.getTanggal().toString()
                );

        row.createCell(1)
                .setCellValue(
                        t.getNama()
                );

        row.createCell(2)
                .setCellValue(
                        t.getKategori()
                );

        row.createCell(3)
                .setCellValue(
                        t.getJenis()
                );

        row.createCell(4)
                .setCellValue(
                        t.getJumlah()
                );
    }

    for(int i = 0; i < 5; i++){
        sheet.autoSizeColumn(i);
    }

    response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    );

    response.setHeader(
            "Content-Disposition",
            "attachment; filename=laporan-keuangan.xlsx"
    );

    workbook.write(response.getOutputStream());

    workbook.close();
}
}