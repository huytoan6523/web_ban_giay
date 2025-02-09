package datn.be.mycode.RESTController;

import datn.be.mycode.request.customRequest.TableThaoTacHoaDonRequest;
import datn.be.mycode.service.ThaoTacHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/thao_tac_hoa_don")
public class ThaoTacHoaDonController {

    @Autowired
    private ThaoTacHoaDonService thaoTacHoaDonService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "idHoaDon",required = false) Long idHoaDon,
            @RequestParam(name = "idNhanVien",required = false) Long idNhanVien
    ){

        TableThaoTacHoaDonRequest request = new TableThaoTacHoaDonRequest(page, pageSize, idHoaDon, idNhanVien, status);
        return ResponseEntity.ok(thaoTacHoaDonService.getAll(request));
    }

//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestBody ThaoTacHoaDonAddRequest thaoTacHoaDonRequest){
//
//        return ResponseEntity.ok(thaoTacHoaDonService.add(thaoTacHoaDonRequest));
//    }

//    @PutMapping("update")
//    public ResponseEntity<?> update(@RequestBody SanPhamRequest request){
//
//        return ResponseEntity.ok(thaoTacHoaDonService.update(request));
//    }
//
//    @PutMapping("/update-trang-thai/{id}")
//    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
//                                             @RequestParam(name = "status",required = true) Integer status
//    ){
//        return ResponseEntity.ok(thaoTacHoaDonService.udateTrangThai(id, status));
//    }

}
