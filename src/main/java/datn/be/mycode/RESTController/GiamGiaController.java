package datn.be.mycode.RESTController;


import datn.be.mycode.request.GiamGiaRequest;
import datn.be.mycode.request.LoaiCoRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.service.GiamGiaService;
import datn.be.mycode.service.LoaiCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/giam_gia")
public class GiamGiaController {
    @Autowired
    private GiamGiaService giamGiaService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(giamGiaService.getAll(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllList(){
        return ResponseEntity.ok(giamGiaService.getAll());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getByIdSanPham(
            @PathVariable(name = "id",required = false) Long id
    ){
        return ResponseEntity.ok(giamGiaService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestBody GiamGiaRequest request
    ){
        return ResponseEntity.ok(giamGiaService.add(request));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody GiamGiaRequest request){

        return ResponseEntity.ok(giamGiaService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(giamGiaService.udateTrangThai(id, status));
    }


}
