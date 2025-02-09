package datn.be.mycode.service;

import datn.be.mycode.config.JwtTokenProvider;
import datn.be.mycode.entity.NhanVien;
import datn.be.mycode.entity.loginTest;
import datn.be.mycode.repository.ChucVuRepository;
import datn.be.mycode.repository.NhanVienRepository;

import datn.be.mycode.request.NhanVienRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.customRequest.TableNhanVienRequest;
import datn.be.mycode.response.LoginResponse;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.UserLoginResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository repo;
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Long idNhanVien = Long.valueOf(0);

    public NormalTableResponse<NhanVien> getAll(TableNhanVienRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        NormalTableResponse<NhanVien> nhanvienNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        Page<NhanVien> nhanvienPage;

            nhanvienPage = repo.searchAll(request, pageable);
            if(nhanvienPage.getTotalElements() == 0){
                nhanvienPage = repo.searchAllBySDT(request, pageable);
                if(nhanvienPage.getTotalElements() == 0){
                    nhanvienPage = repo.searchAllByUsername(request, pageable);
                    if(nhanvienPage.getTotalElements() == 0){
                        nhanvienPage = repo.searchAllByEmail(request, pageable);
                    }
                }
            }


            List<NhanVien> nhanviens = nhanvienPage.getContent().stream().toList();

            nhanvienNormalTableResponse.setItem(nhanviens);
            nhanvienNormalTableResponse.setPage(request.getPage());
            nhanvienNormalTableResponse.setPageSize(request.getPageSize());
            nhanvienNormalTableResponse.setTotalItem(nhanvienPage.getTotalElements());

            return nhanvienNormalTableResponse;
    }

    public List<NhanVien> getAll() {
        return repo.findAll();
    }

    public NhanVien getById(Long id){
        return repo.findById(id).get();
    }

    public ResponseEntity<?> add(NhanVienRequest request) {

        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);

        NhanVien nhanvien = NhanVien.builder()
                .ma(request.getMa())
                .hoTen(request.getHoten())
                .ngaySinh(request.getNgaySinh())
                .ngayTao(LocalDateTime.now())
                .soDienThoai(request.getSoDienThoai())
                .gioiTinh(request.getGioiTinh())
                .hinhAnh(request.getHinhAnh())
                .diaChi(request.getDiaChi())
                .email(request.getEmail())
                .taiKhoan(request.getTaiKhoan())
                .matKhau(request.getMatKhau())
                .chucVu(chucVuRepository.findById(request.getIdChucVu()).get())
                .trangThai(1)
                .build();
        nhanvien = repo.save(nhanvien);
        nhanvien.setMa("NV"+nhanvien.getId());
        return ResponseEntity.ok(repo.save(nhanvien));
    }

    private String check(NhanVienRequest request) {
        if (request.getHoten()== null ||request.getHoten().isBlank()) return "Bạn chưa nhập tên";
        if (request.getSoDienThoai()== null ||request.getSoDienThoai().isBlank()) return "Bạn chưa nhập số điện thoại";
        if (request.getDiaChi()== null ||request.getDiaChi().isBlank()) return "Bạn chưa nhập địa chỉ";
        if (request.getEmail()== null ||request.getEmail().isBlank()) return "Bạn chưa nhập email";
        if (request.getTaiKhoan()== null ||request.getTaiKhoan().isBlank()) return "Bạn chưa nhập tai khoản";
        if (request.getMatKhau()== null ||request.getMatKhau().isBlank()) return "Bạn chưa nhập mật khẩu";
        return "";
    }


    public ResponseEntity<?> update(NhanVienRequest request) {

        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);

        NhanVien nhanvien = repo.findById(request.getId()).get();
        nhanvien.setMa(request.getMa());
        nhanvien.setHoTen(request.getHoten());
        nhanvien.setNgaySinh(request.getNgaySinh());
        nhanvien.setSoDienThoai(request.getSoDienThoai());
        nhanvien.setGioiTinh(request.getGioiTinh());
        nhanvien.setDiaChi(request.getDiaChi());
        nhanvien.setHinhAnh(request.getHinhAnh());
        nhanvien.setEmail(request.getEmail());
        nhanvien.setTaiKhoan(request.getTaiKhoan());
        nhanvien.setMatKhau(request.getMatKhau());
        nhanvien.setChucVu(chucVuRepository.findById(request.getIdChucVu()).get());
        nhanvien.setTrangThai(request.getTrangThai());

        return ResponseEntity.ok(repo.save(nhanvien));
    }


    public void delete(NhanVien nhanvien) {
        repo.delete(nhanvien);
    }


    public NhanVien deleteById(Long id) {
        NhanVien nhanvien = repo.findById(id).get();
        nhanvien.setTrangThai(0);
        return repo.save(nhanvien);
    }

    public NhanVien udateTrangThai(Long id, Integer status){
        NhanVien nhanvien = repo.findById(id).get();
        nhanvien.setTrangThai(status);
        return repo.save(nhanvien);
    }

    public ResponseEntity<?> getAccount(loginTest loginRequest) {

        NhanVien nhanVien = repo.getAccount(loginRequest);
        Map<String, String> response = new HashMap<>();

        if (nhanVien != null) {

            UserLoginResponse user = UserLoginResponse.builder()
                    .id(nhanVien.getId())
                    .hoTen(nhanVien.getHoTen())
                    .hinhAnh(nhanVien.getHinhAnh())
                    .ngaySinh(nhanVien.getNgaySinh()+"")
                    .ngayTao(nhanVien.getNgayTao()+"")
                    .soDienThoai(nhanVien.getSoDienThoai())
                    .gioiTinh(nhanVien.getGioiTinh())
                    .email(nhanVien.getEmail())
                    .taiKhoan(nhanVien.getTaiKhoan())
                    .matKhau(nhanVien.getMatKhau())
                    .chucVu(nhanVien.getChucVu().toMap())
                    .trangThai(nhanVien.getTrangThai())
                    .build();

            String token = jwtTokenProvider.generateToken(nhanVien.getTaiKhoan(), nhanVien.getChucVu().getTen(), user);
            response.clear();
            response.put("token", token);
            this.idNhanVien = nhanVien.getId();
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(new erroResponse("Tài khoản hoặc mật khẩu không chính xác"));

    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
