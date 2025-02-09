package datn.be.mycode.RESTController;

import datn.be.mycode.request.SuKien.SuKienRequest;
import datn.be.mycode.request.SuKien.SuKienUpdateReq;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.service.SuKienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/su_kien")
public class SuKienController {
    @Autowired
    SuKienService service;

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

    @GetMapping("/get-detail-su-kien/{idSuKien}")
    public ResponseEntity<?> getDetailSuKien(@PathVariable Long idSuKien){
        return ResponseEntity.ok(service.getDetailSuKien(idSuKien));
    }

    @GetMapping("/get-su-kien-active")
    public ResponseEntity<?> getSuKienActive() {
        service.loadSuKien();
        return ResponseEntity.ok(service.getSuKienActive());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SuKienRequest suKienRequest){

        return ResponseEntity.ok(service.add(suKienRequest));
    }

    @PostMapping("/get-thang-active")
    public ResponseEntity<?> getThangActive(){

        return ResponseEntity.ok(service.getThangActive());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SuKienUpdateReq request){

        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping("/update-trangthai-auto")
    public ResponseEntity<?> capNhatTrangThaiSuKien() {
        service.loadSuKien();
        return ResponseEntity.ok("Cập nhật trạng thái sự kiện tự động thành công.");
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer trangThai){
        return ResponseEntity.ok(service.udateTrangThai(id, trangThai));
    }
}
