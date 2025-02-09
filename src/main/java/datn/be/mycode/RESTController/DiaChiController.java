package datn.be.mycode.RESTController;

import datn.be.mycode.request.DiaChi.DiaChiAddRequest;
import datn.be.mycode.request.DiaChi.DiaChiRequest;
import datn.be.mycode.request.customRequest.TableDiaChiKhachHangRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dia_chi")
public class DiaChiController {
    @Autowired
    private DiaChiService diaChiService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) Long keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        TableLongRequest request = new TableLongRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(diaChiService.getAll(request));
    }

    @GetMapping("get-by-idkh")
    public ResponseEntity<?> getByIdkh(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name="idKhachHang",required = false) Long idKhachHang
    ){
        TableDiaChiKhachHangRequest request = new TableDiaChiKhachHangRequest(page,pageSize,idKhachHang);
        return ResponseEntity.ok(diaChiService.getDiaChiByIdKhachHang(request));
    }

    @GetMapping("set-mac-dinh/{idDiaChi}")
    public ResponseEntity<?> setMacDinh( @PathVariable Long idDiaChi){

        return ResponseEntity.ok(diaChiService.setMacDinh(idDiaChi));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody DiaChiAddRequest tenDiaChiRequest){
        System.out.println(tenDiaChiRequest);
        return ResponseEntity.ok(diaChiService.add(tenDiaChiRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody DiaChiRequest request){

        return ResponseEntity.ok(diaChiService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(diaChiService.updateTrangThai(id, status));
    }
}
