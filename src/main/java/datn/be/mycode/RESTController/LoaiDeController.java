package datn.be.mycode.RESTController;


import datn.be.mycode.request.LoaiCoRequest;
import datn.be.mycode.request.LoaiDeRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.service.LoaiCoService;
import datn.be.mycode.service.LoaiDeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/loai_de")
public class LoaiDeController {
    @Autowired
    private LoaiDeService loaiDeService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(loaiDeService.getAll(request));
    }

    @GetMapping("/get-all-loai-de")
    public ResponseEntity<?> getAllLoaiDe(){
        return ResponseEntity.ok(loaiDeService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody(required = false) String tenThuongHieuRequest){
        System.out.println(tenThuongHieuRequest);
        return ResponseEntity.ok(loaiDeService.add(tenThuongHieuRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody LoaiDeRequest request){

        return ResponseEntity.ok(loaiDeService.update(request));
    }



    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(loaiDeService.udateTrangThai(id, status));
    }


}
