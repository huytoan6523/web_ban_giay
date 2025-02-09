package datn.be.mycode.RESTController;

import datn.be.mycode.request.ChucVuRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.TheLoaiRequest;
import datn.be.mycode.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/chuc_vu")
public class ChucVuController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(chucVuService.getAll(request));
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenChuVuRequest){

        return ResponseEntity.ok(chucVuService.add(tenChuVuRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ChucVuRequest request){

        return ResponseEntity.ok(chucVuService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(chucVuService.udateTrangThai(id, status));
    }

}
