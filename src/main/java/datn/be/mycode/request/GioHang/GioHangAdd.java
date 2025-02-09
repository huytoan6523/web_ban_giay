package datn.be.mycode.request.GioHang;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GioHangAdd {

    private String ghiChu;

    private String tenNguoiNhan;

    private String sdtNguoiNhan;

    private String idTinhThanh;

    private String tinhThanh;

    private String idQuanHuyen;

    private String quanHuyen;

    private String idPhuongXa;

    private String phuongXa;

    private BigDecimal phiShip;

    private BigDecimal tongTien;

    private BigDecimal tongTienSanPhamChuaGiam;

    private BigDecimal tongTienGiam;

    private Long idPhuongThucThanhToan;

    private Long idKhachHang;

}
