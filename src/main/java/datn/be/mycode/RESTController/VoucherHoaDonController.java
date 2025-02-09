package datn.be.mycode.RESTController;

import datn.be.mycode.entity.Voucher;
import datn.be.mycode.entity.VoucherHoaDon;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.VoucherHoaDonRequest;
import datn.be.mycode.request.customRequest.TableVoucherHoaDonRequest;
import datn.be.mycode.service.VoucherHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher_hoa_don")
public class VoucherHoaDonController {
    @Autowired
    private VoucherHoaDonService service;

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
    public ResponseEntity<?> add(@RequestBody VoucherHoaDonRequest tenVoucherRequest){
        System.out.println(tenVoucherRequest);
        return ResponseEntity.ok(service.add(tenVoucherRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody VoucherHoaDonRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(service.updateTrangThai(id, status));
    }

    @GetMapping("/get-by-id/{idHoaDon}")
    public ResponseEntity<List<Voucher>> getVouchersByHoaDon(@PathVariable Long idHoaDon) {
        List<Voucher> vouchers = service.getVouchersByHoaDonId(idHoaDon);
        return ResponseEntity.ok(vouchers);
    }

}
