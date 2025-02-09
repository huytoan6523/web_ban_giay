package datn.be.mycode.RESTController;

import datn.be.mycode.request.ThongBao.ThongBaoGet;
import datn.be.mycode.service.ThongBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/thong_bao")
public class ThongBaoController {

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/get-thong-bao")
    public ResponseEntity<?> getThongBao(@RequestParam(name = "page",required = false) Integer page,
                                         @RequestParam(name = "pageSize",required = false) Integer pageSize,
                                         @RequestParam(name = "idKhachHang",required = false) Long idKhachHang){
        ThongBaoGet request = new ThongBaoGet(page, pageSize, idKhachHang);
        return ResponseEntity.ok(thongBaoService.getThongBao(request));
    }

    @GetMapping("/get-count-thong-bao/{idKhachHang}")
    public ResponseEntity<?> getCountThongBao(@PathVariable Long idKhachHang){
        return ResponseEntity.ok(thongBaoService.getCountThongBao(idKhachHang));
    }

    @GetMapping("/set-thong-bao-da-doc/{idKhachHang}")
    public ResponseEntity<?> setThongBaoDaDoc(@PathVariable Long idKhachHang){
        return ResponseEntity.ok(thongBaoService.setThongBao(idKhachHang));
    }

    @GetMapping("/set-thong-bao-tung-cai/{idThongBao}")
    public ResponseEntity<?> setThongBaoDaDocTungCai(@PathVariable Long idThongBao){
        return ResponseEntity.ok(thongBaoService.setThongBaoTungCai(idThongBao));
    }

    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody String message) {
        // Gửi thông báo đến topic "/topic/notifications"
        messagingTemplate.convertAndSend("/topic/notifications", message);
        return "Notification sent!";
    }
}
