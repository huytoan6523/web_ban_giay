package datn.be.mycode.response;


import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.entity.NhanVien;
import datn.be.mycode.entity.PhuongThucThanhToan;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThaoTacHoaDonResponse {

    private Long id;

    private String thaoTac;

    private LocalDateTime ngayTao;

    private int trangThai;

    private HoaDon hoaDon;

    private NhanVien nhanVien;

}
