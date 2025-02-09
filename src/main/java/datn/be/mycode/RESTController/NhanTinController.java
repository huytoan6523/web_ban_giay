package datn.be.mycode.RESTController;

import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.response.NhanTin.TrangThai;
import datn.be.mycode.service.KhachHangService;
import datn.be.mycode.service.NhanTinService;
import datn.be.mycode.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nhan-tin")
public class NhanTinController {

    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private NhanTinService nhanTinService;

    @GetMapping("/get-all-nhan-vien")
    public ResponseEntity<?> getAllNhanVien(){
        return ResponseEntity.ok(nhanVienService.getAll());
    }

    @GetMapping("/get-all-khach-hang")
    public ResponseEntity<?> getAllKhachHang(){
        return ResponseEntity.ok(khachHangService.getAll());
    }

    @GetMapping("/get-hop-thoai-by-nhan-vien/{idNhanVien}")
    public ResponseEntity<?> getHopThoaiByNhanVien(
            @PathVariable("idNhanVien") Long idNhanVien,
            @RequestParam(name = "keyWord",required = false) String keyWord
    ){
        return ResponseEntity.ok(nhanTinService.getHopThoaiNhanVienByIdNhanVien(idNhanVien, keyWord));
    }

    @PutMapping("/update-trang-thai-by-hoi-thoai-nhan-vien")
    public ResponseEntity<?> updateTrangThaiByIdHoiThoaiNhanVien(@RequestBody TrangThai request){
        return ResponseEntity.ok(nhanTinService.updateTrangThaiByIdHoiThoaiNhanVien(request));
    }

}
