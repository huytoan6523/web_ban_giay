package datn.be.mycode.request;

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

public class HoaDonRequest {

    private Long id;

    private String ma;

    private LocalDateTime ngayTao;

    private LocalDateTime NgayThanhToan;

    private String ghiChu;

    private String tenNguoiNhan;

    private String sdtNguoiNhan;

    private String diaChiNguoiNhan;

    private BigDecimal phiShip;

    private BigDecimal tongTien;

    private BigDecimal tongTienSanPhamChuaGiam;

    private BigDecimal tongTienGiam;

    private Long idPhuongThucThanhToan;

    private Long idNhanVien;

    private Long idKhachHang;

    private int trangThai;

}
