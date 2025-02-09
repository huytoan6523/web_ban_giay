package datn.be.mycode.RESTController;

import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.TheLoaiRequest;
import datn.be.mycode.request.TrangThaiRequest;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/the_loai")
public class TheLoaiController {

    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(theLoaiService.getAll(request));
    }
    @GetMapping("/get-all-the-loai")
    public List<TheLoai> findAllTheLoai() {
        return theLoaiService.findAllTheLoai();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenTheLoaiRequest){

        return ResponseEntity.ok(theLoaiService.add(tenTheLoaiRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody TheLoaiRequest request){

        return ResponseEntity.ok(theLoaiService.update(request));
    }

    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(theLoaiService.udateTrangThai(id, status));
    }
}
