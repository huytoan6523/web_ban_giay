package datn.be.mycode.RESTController;


import datn.be.mycode.entity.LoaiCo;
import datn.be.mycode.repository.LoaiCoRepository;
import datn.be.mycode.request.LoaiCoRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.ThuongHieuRequest;
import datn.be.mycode.service.LoaiCoService;
import datn.be.mycode.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/loai_co")
public class LoaiCoController {
    @Autowired
    private LoaiCoService loaiCoService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(loaiCoService.getAll(request));
    }

    @GetMapping("/get-all-loai-co")
    public ResponseEntity<?> getAllLoaCO(){
        return ResponseEntity.ok(loaiCoService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenThuongHieuRequest){
        System.out.println(tenThuongHieuRequest);
        return ResponseEntity.ok(loaiCoService.add(tenThuongHieuRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody LoaiCoRequest request){

        return ResponseEntity.ok(loaiCoService.update(request));
    }



    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(loaiCoService.udateTrangThai(id, status));
    }


}
