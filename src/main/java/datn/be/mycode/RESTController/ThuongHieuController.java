package datn.be.mycode.RESTController;


import datn.be.mycode.entity.ThuongHieu;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.ThuongHieuRequest;
import datn.be.mycode.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/thuong_hieu")
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(thuongHieuService.getAll(request));
    }



    @GetMapping("/get-all-thuong-hieu")
    public List<ThuongHieu> findAllThuongHieu() {
        return thuongHieuService.findAllThuongHieu();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenThuongHieuRequest){
        System.out.println(tenThuongHieuRequest);
        return ResponseEntity.ok(thuongHieuService.add(tenThuongHieuRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ThuongHieuRequest request){

        return ResponseEntity.ok(thuongHieuService.update(request));
    }



    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(thuongHieuService.udateTrangThai(id, status));
    }


}
