package datn.be.mycode.util;

import datn.be.mycode.service.HoaDonService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelGenerator {
    @Autowired
    private HoaDonService hoaDonService;

    public void createExcel() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(dtf);
        String filePath = "C:\\Users\\PC\\OneDrive\\Máy tính\\thong_ke_" + timestamp + ".xlsx";

        var hoaDonList = hoaDonService.getHoaDonHomNay();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoa don");

        // Tạo tiêu đề
        Row headerRow = sheet.createRow(0);
        Cell cell1 = headerRow.createCell(0);
        cell1.setCellValue("Mã hóa đơn");
        Cell cell2 = headerRow.createCell(1);
        cell2.setCellValue("Khách hàng");
        Cell cell3 = headerRow.createCell(2);
        cell3.setCellValue("Tổng tiền");

        // Thêm một số dữ liệu
        final int[] rowIndex = {1}; // Bắt đầu từ hàng thứ 2 (hàng 1 đã có tiêu đề)
        hoaDonList.forEach(hoaDon -> {
            Row row = sheet.createRow(rowIndex[0]++);
            row.createCell(0).setCellValue(hoaDon.getMa()); // Giả sử bạn có phương thức này
            row.createCell(1).setCellValue(hoaDon.getKhachHang().getHoTen()); // Giả sử bạn có phương thức này
            row.createCell(2).setCellValue(hoaDon.getTongTien()+""); // Giả sử bạn có phương thức này
        });

        // Ghi vào ByteArrayOutputStream
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();

    }
}
