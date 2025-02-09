package datn.be.mycode.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NhanVienResPonse {
    private Long id;

    private String ma;
    private String hoten;
    private LocalDateTime ngaySinh;
    private LocalDateTime ngayTao;
    private String soDienThoai;
    private String gioiTinh;
    private String diaChi;
    private String hinhAnh;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private int trangThai;
}
