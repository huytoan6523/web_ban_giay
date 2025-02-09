package datn.be.mycode.RESTController;

import datn.be.mycode.entity.Voucher;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.VoucherKhachHangRequest;
import datn.be.mycode.service.VoucherKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher_khach_hang")
public class VoucherKhachHangController {

    @Autowired
    VoucherKhachHangService service;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(service.getAll(request));
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody VoucherKhachHangRequest tenvckhRequest){
        System.out.println(tenvckhRequest);
        return ResponseEntity.ok(service.add(tenvckhRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody VoucherKhachHangRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(service.udateTrangThai(id, status));
    }

    @GetMapping("/get-by-id/{idKhachHang}")
    public ResponseEntity<?> getVouchersByHoaDon(@PathVariable() Long idKhachHang) {
        var vouchers = service.getVouchersByKhachHangId(idKhachHang);
        return ResponseEntity.ok(vouchers);
    }
}
