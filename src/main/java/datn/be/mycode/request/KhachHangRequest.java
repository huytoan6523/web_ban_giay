package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class KhachHangRequest {

    private Long id;
    @NotBlank(message = "ko dc trong")
    private String ma;
    private String hoTen;
    private String hinhAnh;
    private LocalDateTime ngaySinh;
    private Date ngayTao;

    private String soDienThoai;
    private int gioiTinh;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private int trangThai;
    private String confirmPassword;

}
