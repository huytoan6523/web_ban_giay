package datn.be.mycode.request.BanHangOnline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThanhToanKhiNhanHang {

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

    private List<Long> idVoucherKhachHangList;

    private List<Long> idGioHangChiTietList;

    private Long idKhachHang;
}
