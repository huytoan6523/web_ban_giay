package datn.be.mycode.response.DoiTra;

import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.entity.SanPhamChiTiet;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietDoiTraRes {
    private Long id;

    private int soLuong;

    private BigDecimal giaHienHanh;

    private BigDecimal giaDaGiam;

    private int trangThai;

    private boolean danhGia;

    private HoaDon hoaDon;

    private SanPhamChiTiet sanPhamChiTiet;

    private Integer soLuongDoi;

    public void HoaDonChiTietDoiTraRes(HoaDonChiTiet hoaDonChiTiet) {
        this.id = hoaDonChiTiet.getId();
        this.soLuong = hoaDonChiTiet.getSoLuong();
        this.giaHienHanh = hoaDonChiTiet.getGiaHienHanh();
        this.giaDaGiam = hoaDonChiTiet.getGiaDaGiam();
        this.trangThai = hoaDonChiTiet.getTrangThai();
        this.danhGia = hoaDonChiTiet.isDanhGia();
        this.hoaDon = hoaDonChiTiet.getHoaDon();
        this.sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
    }
}
