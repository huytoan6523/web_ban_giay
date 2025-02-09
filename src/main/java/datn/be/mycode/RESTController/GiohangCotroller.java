package datn.be.mycode.RESTController;

import datn.be.mycode.request.BanHangOnline.ThanhToanKhiNhanHang;
import datn.be.mycode.request.GioHang.GioHangDiaChi;
import datn.be.mycode.request.GioHang.GioHangAdd;
import datn.be.mycode.request.GioHang.GioHangPhuongThucThanhToan;
import datn.be.mycode.request.GioHang.GioHangVoucher;
import datn.be.mycode.request.customRequest.BanHang.AddObject;
import datn.be.mycode.request.customRequest.BanHang.AddVoucher;
import datn.be.mycode.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/gio_hang")
public class GiohangCotroller {

    @Autowired
    private GioHangService gioHangService;

    @GetMapping("/{idKhachHang}")
    public ResponseEntity<?> getAll(@PathVariable("idKhachHang") Long idKhachHang){
        return ResponseEntity.ok(gioHangService.getByKhachHang(idKhachHang).getBody());
    }

    @GetMapping("/get_so_luong/{idKhachHang}")
    public ResponseEntity<?> getSoLuong(@PathVariable("idKhachHang") Long idKhachHang){
        return ResponseEntity.ok(gioHangService.getSoLuong(idKhachHang).getBody());
    }

    @PutMapping("/add-dia-chi")
    public ResponseEntity<?> addDiaChi(@RequestBody() GioHangDiaChi request){

        return ResponseEntity.ok(gioHangService.addDiaChi(request));
    }

    @PutMapping("/add-voucher")
    public ResponseEntity<?> addVoucher(@RequestBody() GioHangVoucher request){

        return ResponseEntity.ok(gioHangService.addVoucher(request));
    }

    @PutMapping("/add-phuong-thuc-thanh-toan")
    public ResponseEntity<?> addPhuongThucThanhToan(@RequestBody() GioHangPhuongThucThanhToan request){

        return ResponseEntity.ok(gioHangService.addPhuongThucThanhToan(request));
    }

    @PutMapping("/thanh-toan-khi-giao-hang")
    public ResponseEntity<?> thanhToanKhiGiaohang(@RequestBody() ThanhToanKhiNhanHang request){

        return ResponseEntity.ok(gioHangService.thanhToanKhiGiaohang(request));
    }

    @PutMapping("/thanh-toan-online")
    public ResponseEntity<?> thanhToanOnline(@RequestBody() ThanhToanKhiNhanHang request){

        return ResponseEntity.ok(gioHangService.thanhToanOnline(request));
    }

    @PutMapping("/thanh-toan-thanh-cong")
    public ResponseEntity<?> thanhToanThanhCong(@RequestBody() ThanhToanKhiNhanHang request){

        return ResponseEntity.ok(gioHangService.thanhToanThanhCong(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGioHang(@RequestBody() GioHangAdd request){
        ResponseEntity<?> responseEntity = gioHangService.updateGioHang(request);
        return responseEntity;
    }

}
