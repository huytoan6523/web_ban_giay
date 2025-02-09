package datn.be.mycode.RESTController;

import datn.be.mycode.entity.DanhGiaSanPham;
import datn.be.mycode.request.DanhGiaSanPhamRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.request.danhGiaSanPham.DanhGiaByKhachHangIdRequest;
import datn.be.mycode.request.danhGiaSanPham.DanhGiaBySanPhamIdRequest;
import datn.be.mycode.request.khachHang.DanhGiaSanPhamChungRequest;
import datn.be.mycode.service.DanhGiaSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danh_gia_san_pham")
public class DanhGiaSanPhamController {
    @Autowired
    DanhGiaSanPhamService danhGiaSanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam Long idKhachHang,
            @RequestParam(name = "status",required = false) Integer status
    ){

        TableLongRequest request = new TableLongRequest(page,pageSize,idKhachHang,status);
        return ResponseEntity.ok(danhGiaSanPhamService.getAll(request));
    }


    @GetMapping("/get-by-khach-hang-id")
    public ResponseEntity<?> getDanhGiaByKhachHangId(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam Long idKhachHang,
            @RequestParam(name = "status",required = false) Integer status) {

        DanhGiaByKhachHangIdRequest request = new DanhGiaByKhachHangIdRequest(page,pageSize,idKhachHang,status);
        return ResponseEntity.ok(danhGiaSanPhamService.getDanhGiaByKhachHangId(request));
    }

    @GetMapping("/get-by-san-pham-id")
    public ResponseEntity<?> getDanhGiaBySanPhamId(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam Long idSanPham,
            @RequestParam(name = "status",required = false) Integer status) {

        DanhGiaBySanPhamIdRequest request = new DanhGiaBySanPhamIdRequest(page,pageSize,idSanPham,status);
        return ResponseEntity.ok(danhGiaSanPhamService.getDanhGiaBySanPhamId(request));
    }

//    @PutMapping("/update")
//    public ResponseEntity<?> update(@RequestBody DanhGiaSanPhamRequest request){
//
//        return ResponseEntity.ok(danhGiaSanPhamService.update(request));
//    } hop/15/11/2024

    // ẩn/hiện đánh giá
    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer trangThai){
        return ResponseEntity.ok(danhGiaSanPhamService.udateTrangThai(id, trangThai));
    }

// add 1sp
    @PostMapping("/danh-gia-tung-san-pham")
    public ResponseEntity<DanhGiaSanPham> danhGiaTungSanPham(@RequestBody DanhGiaSanPhamChungRequest request) {
        DanhGiaSanPham danhGia = danhGiaSanPhamService.danhGiaTungSanPham(request);
    return ResponseEntity.ok(danhGia);
    }

// add danh gia tất cả
    @PostMapping("/danh-gia-tat-ca")
    public ResponseEntity<List<DanhGiaSanPham>> addRatingsForPurchasedProducts(
        @RequestParam Long idKhachHang,
        @RequestBody DanhGiaSanPhamChungRequest request) {

        List<DanhGiaSanPham> danhGiaList = danhGiaSanPhamService.danhGiaTatCaSanPham(idKhachHang, request);
        return ResponseEntity.ok(danhGiaList);
    }
    // update đánh giá
    @PutMapping("/danh-gia-cap-nhat")
    public ResponseEntity<?> capNhatDanhGiaSanPham(@RequestBody DanhGiaSanPhamRequest request) {
        return danhGiaSanPhamService.updateDanhGiaSanPham(request);
    }

    // xóa đánh giá
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> xoaDanhGiaSanPham(@PathVariable Long id) {
        return danhGiaSanPhamService.deleteDanhGiaSanPham(id);
    }

}
