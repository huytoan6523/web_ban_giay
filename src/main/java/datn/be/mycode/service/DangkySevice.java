package datn.be.mycode.service;

import datn.be.mycode.entity.GioHang;
import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.repository.GioHangRepository;
import datn.be.mycode.repository.KhachHangRepository;
import datn.be.mycode.request.DangKy.DangKy;
import datn.be.mycode.request.DangKy.DangKyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Validated

@ControllerAdvice
@RestControllerAdvice
public class DangkySevice {
    @Autowired
    private KhachHangRepository repository;
    @Autowired
    private GioHangRepository gioHangRepository;
    @Autowired
    private JavaMailSender mailSender;

    private void creatGioHang(KhachHang khachHang) {
        var setGia = BigDecimal.valueOf(0);
        GioHang gioHang = GioHang.builder()
                .ngayTao(LocalDateTime.now())
                .phiShip(setGia)
                .tongTien(setGia)
                .tongTienGiam(setGia)
                .tongTienGiam(setGia)
                .trangThai(1)
                .khachHang(khachHang)
                .build();
        gioHang = gioHangRepository.save(gioHang);
        gioHang.setMa("GH"+gioHang.getId());
        gioHangRepository.save(gioHang);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    public boolean existsByTaiKhoan(String taiKhoan) {
        return repository.existsByTaiKhoan(taiKhoan);
    }



    public KhachHang add(DangKy request) {

        KhachHang khachHang = new KhachHang();
        khachHang.setMa("");
        khachHang.setHoTen(request.getTenNguoiDung());
        khachHang.setSoDienThoai(request.getSDT());
        khachHang.setNgayTao(LocalDateTime.now());
        khachHang.setEmail(request.getEmail());
        khachHang.setTaiKhoan(request.getTaiKhoan());
        khachHang.setMatKhau(request.getMatKhau());
        khachHang.setTrangThai(1);

        khachHang = repository.save(khachHang);
        khachHang.setMa("KH" + khachHang.getId());
        creatGioHang(khachHang);

        return repository.save(khachHang);
    }

    public KhachHang update(DangKyRequest request) {
        KhachHang khachHang = repository.findById(request.getId()).get();
        khachHang.setMa(request.getMa());
        khachHang.setHoTen(request.getHoTen());

        khachHang.setHinhAnh(request.getHinhAnh());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setTrangThai(request.getTrangThai());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setGioiTinh(request.getGioiTinh());

        return repository.save(khachHang);
    }


}
