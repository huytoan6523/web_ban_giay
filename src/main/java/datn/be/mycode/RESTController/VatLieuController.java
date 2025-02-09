package datn.be.mycode.RESTController;


import datn.be.mycode.request.LoaiDeRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.VatLieuRequest;
import datn.be.mycode.service.LoaiDeService;
import datn.be.mycode.service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vat_lieu")
public class VatLieuController {
    @Autowired
    private VatLieuService vatLieuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(vatLieuService.getAll(request));
    }

    @GetMapping("/get-all-vat-lieu")
    public ResponseEntity<?> getAllVatLieu(){
        return ResponseEntity.ok(vatLieuService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenThuongHieuRequest){

        return ResponseEntity.ok(vatLieuService.add(tenThuongHieuRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody VatLieuRequest request){

        return ResponseEntity.ok(vatLieuService.update(request));
    }



    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(vatLieuService.udateTrangThai(id, status));
    }


}
