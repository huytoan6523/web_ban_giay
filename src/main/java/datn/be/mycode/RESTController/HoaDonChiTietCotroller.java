package datn.be.mycode.RESTController;

import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.entity.SanPham;
import datn.be.mycode.request.HoaDonChiTietRequest;
import datn.be.mycode.request.HoaDonRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.customRequest.BanHang.AddSoLuong;
import datn.be.mycode.request.customRequest.TableHoaDonRequest;
import datn.be.mycode.service.HoaDonChiTietService;
import datn.be.mycode.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hoa_don_chi_tiet")
public class HoaDonChiTietCotroller {

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "idHoaDon",required = false) Long keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord+"",status);
        return ResponseEntity.ok(hoaDonChiTietService.getAllByIdHoaDon(request));
    }
    @GetMapping("/san-pham-chua-cmt")
    public ResponseEntity<List<SanPham>> timSanPhamChuaCmtTrong7NgayThanhToan() {
        List<SanPham> sanPhamList = hoaDonChiTietService.timSanPhamChuaCmtTrong7NgayThanhToan();
        return ResponseEntity.ok(sanPhamList);
    }

    @GetMapping("/san-pham-iscmt-false-by-idkh")
    public ResponseEntity<?> get_NhungSanPhamTrongHoaDonChiTietIsCMTFalseByIdKh(@RequestParam Long idKhachHang) {
        List<SanPham> sanPhams = hoaDonChiTietService.getNhungSanPhamTrongHoaDonChiTietIsCMTFalseByIdKhTrong7Ngay(idKhachHang);
        return ResponseEntity.ok(sanPhams);
    }


    @GetMapping("/hoa-don-get-by-idkh-chua-cmt")
    public ResponseEntity<?> getHoaDonChiTietByKhachHangIdAndWithin7Days(@RequestParam Long idKhachHang) {
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietService.getHoaDonChiTietByKhachHangIdAndWithin7Days(idKhachHang);
        return ResponseEntity.ok(hoaDonChiTietList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HoaDonChiTietRequest request){
        return hoaDonChiTietService.add(request);
    }

    @PostMapping("/add-so-luong")
    public ResponseEntity<?> addSoLuong(@RequestBody AddSoLuong request){
        return hoaDonChiTietService.addSoLuong(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return hoaDonChiTietService.delete(id);
    }
}
