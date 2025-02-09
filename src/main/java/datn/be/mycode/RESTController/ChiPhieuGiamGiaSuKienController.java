package datn.be.mycode.RESTController;

import datn.be.mycode.request.ChiPhieuGGSKRequest;
import datn.be.mycode.request.ChiPhieuGiamGia.AddChiPheuGiamGia;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.service.ChiPhieuGiamGiaSuKienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chi_phieu_giam_gia_su_kien")
public class ChiPhieuGiamGiaSuKienController {
    @Autowired
    ChiPhieuGiamGiaSuKienService service;

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

    @GetMapping("/get-detail/{idSuKien}")
    public ResponseEntity<?> getDetailByIdSuKien(@PathVariable("idSuKien") Long idSuKien){

//        TableLongRequest request = new TableLongRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(service.getDetailByIdSuKien(idSuKien));
    }

//    @GetMapping("/load-su-kien")
//    public ResponseEntity<?> getSuKien(){
//
//        return ResponseEntity.ok(service.loadSuKien());
//    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddChiPheuGiamGia request){

        return ResponseEntity.ok(service.add(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ChiPhieuGGSKRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer trangThai){
        return ResponseEntity.ok(service.udateTrangThai(id, trangThai));
    }
}
