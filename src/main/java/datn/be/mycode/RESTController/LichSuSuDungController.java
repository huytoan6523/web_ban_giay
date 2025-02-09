package datn.be.mycode.RESTController;

import datn.be.mycode.request.LichSuSuDung.LichSuSuDungRequest;
import datn.be.mycode.request.LichSuSuDung.LichSuSuDungUpdateRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.service.LichSuSuDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lich_su_su_dung")
public class LichSuSuDungController {
    @Autowired
    LichSuSuDungService service;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) Long keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        TableLongRequest request = new TableLongRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(service.getAll(request));
    }

    @GetMapping("/voucher-trang-thai-su-dung-by-khach-hang/{idKhachHang}")
    public ResponseEntity<?> getTrangThaiSuDungVoucher(@PathVariable Long idKhachHang){
        return ResponseEntity.ok(service.getTrangThaiSuDungVoucher(idKhachHang));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody LichSuSuDungRequest lichSuSuDungRequest){
        System.out.println(lichSuSuDungRequest);
        return ResponseEntity.ok(service.add(lichSuSuDungRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody LichSuSuDungUpdateRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer trangThai){
        return ResponseEntity.ok(service.udateTrangThai(id, trangThai));
    }
}
