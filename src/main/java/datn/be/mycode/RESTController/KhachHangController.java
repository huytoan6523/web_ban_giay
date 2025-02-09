package datn.be.mycode.RESTController;

import datn.be.mycode.request.KhachHangRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.NormalTableRequestKhachHang;
import datn.be.mycode.request.khachHang.CapNhatEmailKhachHangRequest;
import datn.be.mycode.request.khachHang.CapNhatSoDienThoaiKhachHangRequest;
import datn.be.mycode.request.khachHang.UpdateKhachHangRequest;
import datn.be.mycode.response.erroResponse;
import datn.be.mycode.response.khachHang.OtpResponse;
import datn.be.mycode.response.khachHang.ThongTinKhachHangResponse;
import datn.be.mycode.service.KhachHangService;
import datn.be.mycode.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach_hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    EmailSender emailSender;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status

    ){

        NormalTableRequestKhachHang request = new NormalTableRequestKhachHang(page,pageSize,keyWord,status);
        return ResponseEntity.ok(khachHangService.getAll(request));
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getByIdSanPham(
            @PathVariable(name = "id",required = false) Long id
    ){
        return ResponseEntity.ok(khachHangService.getById(id));
    }


    @GetMapping("/{id}")
    public ThongTinKhachHangResponse getThongTinById(@PathVariable Long id) {
        return khachHangService.getThongTinById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody KhachHangRequest request){

        return ResponseEntity.ok(khachHangService.add(request));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody KhachHangRequest request){

        return ResponseEntity.ok(khachHangService.update(request));
    }

    @PutMapping("update-khach-hang")
    public ResponseEntity<?> updateKhachHang(@RequestBody UpdateKhachHangRequest request){

        return ResponseEntity.ok(khachHangService.updateKhachHang(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){
        return ResponseEntity.ok(khachHangService.udateTrangThai(id, status));
    }

    @PutMapping("/dang-ky")
    public ResponseEntity<?> dangKy(){
        return ResponseEntity.ok("");
    }

    // gửi mã - kiểm tra OTP
//    @PostMapping("gui-ma-otp")
//    public ResponseEntity<?> guiMaOtp(@RequestBody CapNhatEmailKhachHangRequest request) {
//        emailSender.sendEmailOtp(request.getEmail());
//        return ResponseEntity.ok("OTP đã được gửi đến email.");
//    }

    // Gửi mã OTP và trả về mã OTP cho FE
    @PostMapping("gui-ma-otp")
    public ResponseEntity<?> guiMaOtp(@RequestBody CapNhatEmailKhachHangRequest request) {
        try {
            String otp = emailSender.guiEmailOtp(request.getEmail());
            return ResponseEntity.ok(new OtpResponse(otp, "OTP đã được gửi thành công."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new erroResponse("Lỗi khi gửi OTP: " + e.getMessage()));
        }
    }

    // sau khi kiểm tra ok thì cập nhật
    @PutMapping("cap-nhat-so-dien-thoai")
    public ResponseEntity<?> capNhatSoDienThoaiKhachHang(@RequestBody CapNhatSoDienThoaiKhachHangRequest request){

        return ResponseEntity.ok(khachHangService.updateSoDienThoaiKhachHang(request));
    }

    @PutMapping("cap-nhat-email")
    public ResponseEntity<?> capNhatEmailKhachHang(@RequestBody CapNhatEmailKhachHangRequest request){

        return ResponseEntity.ok(khachHangService.updateEmailKhachHang(request));
    }

}
