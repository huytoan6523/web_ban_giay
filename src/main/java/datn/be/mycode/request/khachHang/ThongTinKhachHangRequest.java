package datn.be.mycode.request.khachHang;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ThongTinKhachHangRequest {
    private String ma;
    private String hoTen;
    private String hinhAnh;
    private LocalDateTime ngaySinh;
    private String soDienThoai;
    private int gioiTinh;
    private String email;
    private int trangThai;
}
