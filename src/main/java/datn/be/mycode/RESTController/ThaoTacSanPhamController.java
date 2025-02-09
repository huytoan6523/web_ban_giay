package datn.be.mycode.RESTController;

import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.SanPhamRequest;
import datn.be.mycode.request.ThaoTacSanPhamRequest;
import datn.be.mycode.request.customRequest.TableSanPhamRequest;
import datn.be.mycode.request.customRequest.TableThaoTacSanPham;
import datn.be.mycode.service.SanPhamService;
import datn.be.mycode.service.ThaoTacSanPhamSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/thao_tac_san_pham")
public class ThaoTacSanPhamController {
    @Autowired
    private ThaoTacSanPhamSevice thaotacsanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "startDate",required = false) LocalDateTime startDate
    ){


    TableThaoTacSanPham request = new TableThaoTacSanPham(page,pageSize,keyWord,status,startDate);
         return ResponseEntity.ok(thaotacsanPhamService.getAll(request));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThaoTacSanPhamRequest thaotacsanPhamRequest){

        return ResponseEntity.ok(thaotacsanPhamService.add(thaotacsanPhamRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ThaoTacSanPhamRequest request){

        return ResponseEntity.ok(thaotacsanPhamService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){
        return ResponseEntity.ok(thaotacsanPhamService.udateTrangThai(id, status));
    }

}
