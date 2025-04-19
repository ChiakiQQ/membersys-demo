package com.caitlyn.membersysdemo.service.impl;

import com.caitlyn.membersysdemo.model.Member;
import com.caitlyn.membersysdemo.repo.MemberRepo;
import com.caitlyn.membersysdemo.service.ExportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public void exportMembers(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=members.xlsx");

        List<Member> members = memberRepo.findAll();

        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            CellStyle centerStyle = workbook.createCellStyle();
            centerStyle.setAlignment(HorizontalAlignment.CENTER);

            Sheet sheet = workbook.createSheet("Members");
            Row header = sheet.createRow(0);
            Cell h0 = header.createCell(0);
            h0.setCellValue("ID");
            h0.setCellStyle(centerStyle);

            Cell h1 = header.createCell(1);
            h1.setCellValue("帳號");
            h1.setCellStyle(centerStyle);

            Cell h2 = header.createCell(2);
            h2.setCellValue("Email");
            h2.setCellStyle(centerStyle);

            Cell h3 = header.createCell(3);
            h3.setCellValue("註冊時間");
            h3.setCellStyle(centerStyle);

            int rowIdx = 1;
            for (Member m : members) {
                Row row = sheet.createRow(rowIdx++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(m.getId());
                cell0.setCellStyle(centerStyle);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(m.getUsername());
                cell1.setCellStyle(centerStyle);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(m.getEmail());
                cell2.setCellStyle(centerStyle);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(m.getCreateTime())));
                cell3.setCellStyle(centerStyle);
            }

            sheet.setColumnWidth(0, 3000); // ID
            sheet.setColumnWidth(1, 5000); // username
            sheet.setColumnWidth(2, 8000); // email
            sheet.setColumnWidth(3, 8000); // time
            sheet.setColumnWidth(4, 0); // 硬清除第5欄寬度，防止出現空欄
            workbook.write(out);
        }
    }
}