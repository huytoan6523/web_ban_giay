package datn.be.mycode.RESTController;

import datn.be.mycode.request.DoiTra.PhieuDoiTra.*;
import datn.be.mycode.service.PhieuDoiTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/doi-tra")
public class DoiTraController {

    @Autowired
    private PhieuDoiTraService phieuDoiTraService;

    @GetMapping("/get-phieu-doi-tra-admin")
    public ResponseEntity<?> getPhieuDoiTra(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "theLoai",required = false) Integer theLoai,
            @RequestParam(name = "status",required = false) Integer status
    ){
        GetPhieuDoiTraReq getPhieuDoiTraReq = new GetPhieuDoiTraReq(page, pageSize, keyWord, theLoai, status);
        return ResponseEntity.ok(phieuDoiTraService.getPhieuDoiTra(getPhieuDoiTraReq));
    }

    @GetMapping("/get-phieu-doi-tra-by-khach-hang")
    public ResponseEntity<?> getPhieuDoiTraKhachHang(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "idKhachHang",required = false) Long idKhachHang
    ){
        GetPhieuDoiTraByKhachHangReq request = new GetPhieuDoiTraByKhachHangReq(page, pageSize, idKhachHang);
        return ResponseEntity.ok(phieuDoiTraService.getPhieuDoiTraByKhachHang(request));
    }

    @GetMapping("/get-detail-phieu-doi-tra/{idPhieuDoiTra}")
    public ResponseEntity<?> getDetailPhieuDoiTra(
            @PathVariable("idPhieuDoiTra") Long idPhieuDoiTra
    ){
        return ResponseEntity.ok(phieuDoiTraService.getDetailPhieuDoiTra(idPhieuDoiTra));
    }

    @GetMapping("/get-hoa-don-chi-tiet-cho-khach-hang/{idHoaDon}")
    public ResponseEntity<?> getHoaDonChiTietChoViewKhachHang(@PathVariable Long idHoaDon) {

        return ResponseEntity.ok(phieuDoiTraService.getHoaDonChiTietChoViewKhachHang(idHoaDon));
    }

    @PostMapping("/add-pheu-doi-tra")
    public ResponseEntity<?> addPhieuDoiTra(@RequestBody AddPhieuDoiTraReq request){
        return ResponseEntity.ok(phieuDoiTraService.addPhieuDoiTra(request));
    }

    @PostMapping("/nhan-vien-note")
    public ResponseEntity<?> nhanVienNote(@RequestBody NhanVienNoteReq request){
        return ResponseEntity.ok(phieuDoiTraService.nhanVienNote(request));
    }

    @PutMapping("/nhan-vien-xac-nhan-phieu-doi-tra")
    public ResponseEntity<?> xacNhanPhieuDoiTra(@RequestBody XacNhanReq request){
        return ResponseEntity.ok(phieuDoiTraService.xacNhanPhieuDoiTra(request, 2));
    }

    @PutMapping("/nhan-vien-huy-phieu-doi-tra")
    public ResponseEntity<?> huyPhieuDoiTra(@RequestBody HuyPhieuDoiTraReq request){
        return ResponseEntity.ok(phieuDoiTraService.huyPhieuDoiTra(request));
    }

    @PostMapping("/nhan-vien-kiem-tra-hang")
    public ResponseEntity<?> kiemTraHang(@RequestBody KiemTraHangReq request){
        return ResponseEntity.ok(phieuDoiTraService.nhanVienKiemTra(request, 1));
    }

    @PostMapping("/nhan-vien-gui-bang-chung-thanh-toan")
    public ResponseEntity<?> thongTinThanhToan(@RequestBody KiemTraHangReq request){
        return ResponseEntity.ok(phieuDoiTraService.nhanVienKiemTra(request, 2));
    }

    @PutMapping("/cap-nhat-trang-thai-phieu-doi-tra")
    public ResponseEntity<?> nextStep(
            @RequestBody NextStepDoiTraReq request
    ){
        return ResponseEntity.ok(phieuDoiTraService.nextSetp(request));
    }

    @PutMapping("/thong-tin-chuyen-khoan-phieu-doi-tra")
    public ResponseEntity<?> thongTinChuyenKhoan(
            @RequestBody AddChuyenKhoanReq request
    ){
        return ResponseEntity.ok(phieuDoiTraService.addChuyenKhoan(request));
    }

    @PutMapping("/them-dia-chi-vao-phieu-doi-tra")
    public ResponseEntity<?> addDiaChi(@RequestBody AddDiaChiReq request){
        return ResponseEntity.ok(phieuDoiTraService.taoHoaDon(request));
    }
}
