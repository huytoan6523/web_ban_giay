package datn.be.mycode.RESTController;

import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.PhuongThucThanhToanRequest;
import datn.be.mycode.service.PhuongThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/phuong_thuc_thanh_toan")
public class PhuongThucThanhToanController {

    @Autowired
    private PhuongThucThanhToanService phuongThucThanhToanService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(phuongThucThanhToanService.getAll(request));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody String Request){

        return ResponseEntity.ok(phuongThucThanhToanService.add(Request));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody PhuongThucThanhToanRequest request){

        return ResponseEntity.ok(phuongThucThanhToanService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(phuongThucThanhToanService.udateTrangThai(id, status));
    }

}
