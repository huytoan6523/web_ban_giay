package datn.be.mycode.RESTController;


import datn.be.mycode.request.VoucherRequest;
import datn.be.mycode.request.customRequest.TableVoucherActiveRequest;
import datn.be.mycode.request.customRequest.TableVoucherHoaDonRequest;
import datn.be.mycode.request.customRequest.TableVoucherRequest;
import datn.be.mycode.request.customRequest.TableVoucherStatusInRequest;
import datn.be.mycode.request.voucher.VoucherSuKien;
import datn.be.mycode.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    private VoucherService service;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "theLoai",required = false) Integer theLoai,
            @RequestParam(name = "startDate",required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate",required = false) LocalDateTime endDate,
            @RequestParam(name = "phanTramGiam",required = false) Integer phanTramGiam
    ){

        TableVoucherRequest request = new TableVoucherRequest(page,pageSize,keyWord,status,theLoai,startDate,endDate,phanTramGiam);
        return ResponseEntity.ok(service.getAll(request));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody VoucherRequest tenVoucherRequest){
        System.out.println(tenVoucherRequest);
        return ResponseEntity.ok(service.add(tenVoucherRequest));
    }

    @PostMapping("/add-voucher-su-kien")
    public ResponseEntity<?> addVoucherSuKien(@RequestBody VoucherSuKien request){

        return ResponseEntity.ok(service.addVoucherSuKien(request));
    }

    @GetMapping("/get-phieu-giam-gia-su-kien")
    public ResponseEntity<?> getPhieuGiamGiaVoucher(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize
    ){
        return ResponseEntity.ok(service.getPhieuGiamGiaVoucher(page, pageSize));
    }

    @GetMapping("/get-by-id/{idVoucher}")
    public ResponseEntity<?> getById(
            @PathVariable(name = "idVoucher",required = false) Long idVoucher
    ){
        return ResponseEntity.ok(service.getVoucherById(idVoucher));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody VoucherRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(service.udateTrangThai(id, status));
    }

    @PutMapping("/update-trangthai-auto")
    public ResponseEntity<?> capNhatTrangThaiVoucher() {
        // Gọi phương thức service để thực thi logic cập nhật trạng thái
        service.capNhatTrangThaiVoucher();

        // Trả về phản hồi thành công
        return ResponseEntity.ok("Cập nhật trạng thái voucher tự động thành công.");
    }

//    @GetMapping("trong-su-kien")
//    public ResponseEntity<?> trongSuKien(
//            @RequestParam(name = "page",required = false) Integer page,
//            @RequestParam(name = "pageSize",required = false) Integer pageSize,
//            @RequestParam(name = "status",required = false) Integer status
//    ){
//
//        TableVoucherStatusInRequest request = new TableVoucherStatusInRequest(page,pageSize,status);
//        return ResponseEntity.ok(service.trongSuKien(request));
//    }
    @GetMapping("get-voucher-active")
    public ResponseEntity<?> getVoucherActive(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "giaTriGiamToiThieu",required = false) BigDecimal giaTriGiamToiThieu,
            @RequestParam(name = "theLoai",required = false) Integer theLoai
    ){

        TableVoucherActiveRequest request = new TableVoucherActiveRequest(page,pageSize,giaTriGiamToiThieu,theLoai);
        return ResponseEntity.ok(service.getVoucherActive(request));
    }

    @GetMapping("the-loai-status-active")
    public ResponseEntity<?> checkVoucherActive(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "theLoai",required = false) Integer theLoai
    ){
        TableVoucherStatusInRequest request = new TableVoucherStatusInRequest(page,pageSize,theLoai);
        return ResponseEntity.ok(service.checkVouchers(request));
    }

    @GetMapping("/Voucher-Hoa-Don")
    public ResponseEntity<?> getVoucherHoaDon(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize)
    {
        TableVoucherHoaDonRequest request = new TableVoucherHoaDonRequest(page,pageSize);
        return ResponseEntity.ok(service.getVoucherHoaDon(request));
    }

    @GetMapping("/Voucher-Ship")
    public ResponseEntity<?> getVoucherShip(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize)
    {
        TableVoucherHoaDonRequest request = new TableVoucherHoaDonRequest(page,pageSize);
        return ResponseEntity.ok(service.getVoucherShip(request));
    }

}
