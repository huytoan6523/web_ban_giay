package datn.be.mycode.RESTController;

import datn.be.mycode.repository.NhanVienRepository;
import datn.be.mycode.service.HoaDonService;
import datn.be.mycode.service.NhanVienService;
import datn.be.mycode.util.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeController {

    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ExcelGenerator excelGenerator;


    @GetMapping("/doanh-so")
    public ResponseEntity<?> doanhSoThangNay(){
        var dauThang = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0).withMonth(11);
        var cuoiThang = dauThang.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return ResponseEntity.ok(hoaDonService.getDoanhSo());
    }

//    @GetMapping("/doanh-so-hom-nay")
//    public ResponseEntity<?> doanhSoHomNay(){
//        var dauThang = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
//        var cuoiThang = dauThang.withHour(23).withMinute(59).withSecond(59);
//
//        return ResponseEntity.ok(hoaDonService.getDoanhSoThangNay(dauThang, cuoiThang));
//    }

    @GetMapping("/top-5-san-pham-ban-chay-thang")
    public ResponseEntity<?> sanPhamBanChay(){

        return ResponseEntity.ok(hoaDonService.sanPhamBanChay());
    }

    @GetMapping("/doanh-thu-thang")
    public ResponseEntity<?> doanhThuThang(){

        return ResponseEntity.ok(hoaDonService.getDoanhSoThang());
    }

    @GetMapping("/xuat-excel")
    public ResponseEntity<?> xuatExcel() throws IOException {
        excelGenerator.createExcel();
        return ResponseEntity.ok("Thành công");
    }
}
