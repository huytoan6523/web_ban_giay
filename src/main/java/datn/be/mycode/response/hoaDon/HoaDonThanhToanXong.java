package datn.be.mycode.response.hoaDon;


import datn.be.mycode.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HoaDonThanhToanXong {
    private Long id;
    private String ma;
    private LocalDateTime ngayTao;
    private LocalDateTime NgayThanhToan;
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
    private Boolean thanhToan;
    private Boolean giaoHang;
    private List<Voucher> vouchers;
    private List<HoaDonChiTiet> hoaDonChiTiets;
    private PhuongThucThanhToan phuongThucThanhToan;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private int trangThai;

    public HoaDonThanhToanXong(HoaDon hoaDon, List<Voucher> vouchers, List<HoaDonChiTiet> hoaDonChiTiets) {
        this.id = hoaDon.getId();
        this.ma = hoaDon.getMa();
        this.ngayTao = hoaDon.getNgayTao();
        this.NgayThanhToan = hoaDon.getNgayThanhToan();
        this.ghiChu = hoaDon.getGhiChu();
        this.tenNguoiNhan = hoaDon.getTenNguoiNhan();
        this.sdtNguoiNhan = hoaDon.getSdtNguoiNhan();
        this.idTinhThanh = hoaDon.getIdTinhThanh();
        this.tinhThanh = hoaDon.getTinhThanh();
        this.idQuanHuyen = hoaDon.getIdQuanHuyen();
        this.quanHuyen = hoaDon.getQuanHuyen();
        this.idPhuongXa = hoaDon.getIdPhuongXa();
        this.phuongXa = hoaDon.getPhuongXa();
        this.phiShip = hoaDon.getPhiShip();
        this.tongTien = hoaDon.getTongTien();
        this.tongTienSanPhamChuaGiam = hoaDon.getTongTienSanPhamChuaGiam();
        this.tongTienGiam = hoaDon.getTongTienGiam();
        this.thanhToan = hoaDon.getThanhToan();
        this.giaoHang = hoaDon.getGiaoHang();
        this.vouchers = vouchers;
        this.hoaDonChiTiets = hoaDonChiTiets;
        this.phuongThucThanhToan = hoaDon.getPhuongThucThanhToan();
        this.nhanVien = hoaDon.getNhanVien();
        this.khachHang = hoaDon.getKhachHang();
        this.trangThai = hoaDon.getTrangThai();
    }
}
