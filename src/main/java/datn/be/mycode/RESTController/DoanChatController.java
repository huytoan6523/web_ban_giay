package datn.be.mycode.RESTController;

import datn.be.mycode.request.NhanTin.KhachHang.DoanChat.AddDoanChatKhachHang;
import datn.be.mycode.request.NhanTin.KhachHang.DoanChat.GetByHopThoaiKhachHang;
import datn.be.mycode.request.NhanTin.NhanVien.DoanChat.AddDoanChat;
import datn.be.mycode.request.NhanTin.NhanVien.DoanChat.GetByHopThoaiNhanVien;
import datn.be.mycode.service.DoanChatKhachHangService;
import datn.be.mycode.service.DoanChatNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doan-chat")
public class DoanChatController {

    @Autowired
    private DoanChatNhanVienService doanChatNhanVienService;
    @Autowired
    private DoanChatKhachHangService doanChatKhachHangService;

//    Nhan vien
    @GetMapping("/get-doan-chat-by-hop-thoai")
    public ResponseEntity<?> getDoanChat(
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "idHopThoai",required = false) Long idHopThoai
    ){
        GetByHopThoaiNhanVien request = new GetByHopThoaiNhanVien(page,pageSize,idHopThoai);
        return ResponseEntity.ok(doanChatNhanVienService.getDoanChatNhanVienByHopThoai(request));
    }

    @PostMapping("/add-doan-chat")
    public ResponseEntity<?> addDoanChat(@RequestBody AddDoanChat request){

        return ResponseEntity.ok(doanChatNhanVienService.addDoanChatNhanVien(request));
    }

////    Khach Hang
    @GetMapping("/get-doan-chat-khach-hang-by-hop-thoai")
    public ResponseEntity<?> getDoanChatKhachHang(
           @RequestParam(name = "page",required = false) Integer page,
           @RequestParam(name = "pageSize",required = false) Integer pageSize,
           @RequestParam(name = "idHopThoai",required = false) Long idHopThoai
    ){
    GetByHopThoaiKhachHang request = new GetByHopThoaiKhachHang(page,pageSize,idHopThoai);
    return ResponseEntity.ok(doanChatKhachHangService.getDoanChatKhachHangByHopThoai(request));
    }

    @PostMapping("/add-doan-chat-khach-hang")
    public ResponseEntity<?> addDoanChatKhachHang(@RequestBody AddDoanChatKhachHang request){

        return ResponseEntity.ok(doanChatKhachHangService.addDoanChatKhachHang(request));
    }
}
