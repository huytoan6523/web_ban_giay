package datn.be.mycode.response.khachHang;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ThongTinKhachHangResponse {
    private Long id;
    private String ma;
    private String hoTen;
    private String hinhAnh;
    private LocalDateTime ngaySinh;
    private LocalDateTime ngayTao;
    private String soDienThoai;
    private int gioiTinh;
    private String email;
    private int trangThai;
}
