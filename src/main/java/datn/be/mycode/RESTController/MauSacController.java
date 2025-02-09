package datn.be.mycode.RESTController;

import datn.be.mycode.request.MauSacRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mau_sac")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "keyWord",required = false) String keyWord,
            @RequestParam(name = "status",required = false) Integer status
    ){

        NormalTableRequest request = new NormalTableRequest(page,pageSize,keyWord,status);
        return ResponseEntity.ok(mauSacService.getAll(request));
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody MauSacRequest request){

        return ResponseEntity.ok(mauSacService.add(request));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody MauSacRequest request){

        return ResponseEntity.ok(mauSacService.update(request));
    }



    @PutMapping("/update-trang-thai/{id}")
    public ResponseEntity<?> updateTrangThai(@PathVariable("id") Long id,
                                             @RequestParam(name = "status",required = true) Integer status
    ){

        return ResponseEntity.ok(mauSacService.udateTrangThai(id, status));
    }
}
