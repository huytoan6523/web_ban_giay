package datn.be.mycode.RESTController;


import datn.be.mycode.config.JwtTokenProvider;
import datn.be.mycode.entity.loginTest;
import datn.be.mycode.response.LoginResponse;
import datn.be.mycode.response.erroResponse;
import datn.be.mycode.service.KhachHangService;
import datn.be.mycode.service.NhanVienService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private KhachHangService khachHangService;

    @PostMapping("/nhan_vien_login")
    public ResponseEntity<?> login(@RequestBody loginTest loginRequest) {

        return nhanVienService.getAccount(loginRequest);
    }

    @PostMapping("/khach_hang_login")
    public ResponseEntity<?> loginKhachHang(@RequestBody loginTest loginRequest) {

        return khachHangService.getAccount(loginRequest);
    }

}
