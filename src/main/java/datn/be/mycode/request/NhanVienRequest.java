package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NhanVienRequest {
    private Long id;
    @NotBlank(message = "ko dc trong")
    private String ma;
    private String hoten;
    private LocalDateTime ngaySinh;
    private LocalDateTime ngayTao;
    private String soDienThoai;
    private int gioiTinh;
    private String diaChi;
    private String hinhAnh;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private Long idChucVu;
    private int trangThai;
}
