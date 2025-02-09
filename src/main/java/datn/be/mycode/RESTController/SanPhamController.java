package datn.be.mycode.RESTController;

import datn.be.mycode.entity.DanhGiaSanPham;
import datn.be.mycode.entity.SanPham;
import datn.be.mycode.request.SanPhamRequest;
import datn.be.mycode.request.customRequest.TableSanPhamRequest;
import datn.be.mycode.request.sanPham.SanPhamByAllReq;
import datn.be.mycode.response.sanPham.DanhGiaSanPhamResponse;
import datn.be.mycode.response.sanPham.SanPhamDanhGiaResponse;
import datn.be.mycode.service.ChucVuService;
import datn.be.mycode.service.DanhGiaSanPhamService;
import datn.be.mycode.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/san_pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private DanhGiaSanPhamService danhGiaSanPhamService;


    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "startDate",required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate",required = false) LocalDateTime endDate,
            @RequestParam(name = "giamGia",required = false) Long idGiamGia,
            @RequestParam(name = "thuongHieu",required = false) Long idThuongHieu,
            @RequestParam(name = "theLoai",required = false) Long idTheLoai,
            @RequestParam(name = "loaiCo",required = false) Long idLoaiCo,
            @RequestParam(name = "vatLieu",required = false) Long idVatLieu,
            @RequestParam(name = "loaiDe",required = false) Long idLoaiDe,
            @RequestParam(name = "sort",required = false) Integer sort
    ){

        TableSanPhamRequest request = new TableSanPhamRequest(page,pageSize,keyWord,status, startDate, endDate, idGiamGia, idThuongHieu, idTheLoai, idLoaiCo, idVatLieu, idLoaiDe, sort);
        return ResponseEntity.ok(sanPhamService.getAll(request));
    }

    @GetMapping("/get-by-all")
    public ResponseEntity<?> getAllBy(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "giamGia",required = false) Long idGiamGia,
            @RequestParam(name = "giaCaoNhat",required = false) BigDecimal giaCaoNhat,
            @RequestParam(name = "giaThapNhat",required = false) BigDecimal giaThapNhat,
            @RequestParam(name = "thuongHieu",required = false) Long idThuongHieu,
            @RequestParam(name = "theLoai",required = false) Long idTheLoai,
            @RequestParam(name = "loaiCo",required = false) Long idLoaiCo,
            @RequestParam(name = "vatLieu",required = false) Long idVatLieu,
            @RequestParam(name = "loaiDe",required = false) Long idLoaiDe,
            @RequestParam(name = "sort",required = false) Integer sort
    ){
        SanPhamByAllReq request = new SanPhamByAllReq(page, pageSize, keyWord, idGiamGia, giaCaoNhat, giaThapNhat, idThuongHieu, idTheLoai, idLoaiCo, idVatLieu, idLoaiDe, sort);
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getSort() == null) {
            request.setSort(0);
        }
        return ResponseEntity.ok(sanPhamService.getByAll(request));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(
            @RequestParam(name = "idSanPham",required = false) Long idSanPham
    ){
        return ResponseEntity.ok(sanPhamService.getSanPhamById(idSanPham));
    }

    @GetMapping("/tim-kiem/{tenSanPham}")
    public ResponseEntity<?> timKiem(@PathVariable() String tenSanPham){
        return ResponseEntity.ok(sanPhamService.timKiem(tenSanPham).getBody());
    }

    @GetMapping("/top5-ban-chay")
    public List<SanPham> getTop5SanPhamBanChay() {
        return sanPhamService.top5SanPhamBanChay();
    }

    @GetMapping("/top5-giam-gia")
    public List<SanPham> top5SanPhamGiamGia() {
        return sanPhamService.top5SanPhamGiamGia();
    }

    @GetMapping("/top5-moi")
    public List<SanPham> top5SanPhamMoi() {
        return sanPhamService.top5SanPhamMoi();
    }
    @GetMapping("/top3-giam-gia")
    public List<SanPham> top3SanPhamGiamGia() {
        return sanPhamService.top3SanPhamGiamGia();
    }
    @GetMapping("/giam-gia-sau-nhat")
    public SanPham getSanPhamGiamGiaSauNhat() {
        return sanPhamService.getSanPhamGiamGiaSauNhat();
    }
    @GetMapping("/12-san-pham-update-gan-nhat")
    public List<SanPham> getTop12SanPhamByUpdatedDate() {
        return sanPhamService.getTop12SanPhamByUpdatedDate();
    }
    @GetMapping("/top5-san-pham-theo-the-loai")
    public List<SanPham> getTop5SanPhamGiamGiaTheLoai(@RequestParam Long theLoaiId) {
        return sanPhamService.getTop5SanPhamByTheLoai(theLoaiId);
    }

    @GetMapping("/co-danh-gia")
    public ResponseEntity<?> layDanhSachSanPhamCoDanhGia() {
        List<SanPhamDanhGiaResponse> sanPhamDanhGiaResponses = danhGiaSanPhamService.laySanPhamCoDanhGia();
        return ResponseEntity.ok(sanPhamDanhGiaResponses);
    }

    @GetMapping("/san-pham/{idSanPham}/danh-gia")
    public ResponseEntity<?> layDanhSachDanhGiaSanPham(@PathVariable Long idSanPham) {
        List<DanhGiaSanPhamResponse> danhGiaResponses = danhGiaSanPhamService.layDanhGiaTheoSanPham(idSanPham);
        return ResponseEntity.ok(danhGiaResponses);
    }

    //    @GetMapping("/san-pham-chua-danh-gia/{idKhachHang}")
//    public ResponseEntity<List<Long>> laySanPhamChuaDanhGiaCuaKhachHang(@PathVariable Long idKhachHang) {
//        List<Long> productIds = sanPhamService.laySanPhamChuaDanhGiaCuaKhachHang(idKhachHang);
//        return ResponseEntity.ok(productIds);
//    }
    @GetMapping("/san-pham-chua-danh-gia/{idKhachHang}")
    public ResponseEntity<List<SanPhamRequest>> laySanPhamChuaDanhGiaCuaKhachHang(@PathVariable Long idKhachHang) {
        List<Long> idSanPham = sanPhamService.laySanPhamChuaDanhGiaCuaKhachHang(idKhachHang);
        List<SanPhamRequest> thongTinSanPham = sanPhamService.layThongTinSanPhamTuIds(idSanPham);
        return ResponseEntity.ok(thongTinSanPham);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SanPhamRequest sanPhamRequest){

        return ResponseEntity.ok(sanPhamService.add(sanPhamRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody SanPhamRequest request){

        return ResponseEntity.ok(sanPhamService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){
        return ResponseEntity.ok(sanPhamService.udateTrangThai(id, status));
    }

}
