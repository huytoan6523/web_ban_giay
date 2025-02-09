package datn.be.mycode.response;

import datn.be.mycode.entity.ChucVu;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserLoginResponse {

    private Long id;
    private String hoTen;
    private String hinhAnh;
    private String ngaySinh;
    private String ngayTao;
    private String soDienThoai;
    private int gioiTinh;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private int trangThai;
    private Map<String,String> chucVu;
}
