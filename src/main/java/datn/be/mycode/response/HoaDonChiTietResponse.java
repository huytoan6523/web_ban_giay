package datn.be.mycode.response;


import datn.be.mycode.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HoaDonChiTietResponse {

    private Long id;
    private int soLuong;
    private BigDecimal giaHienHanh;
    private BigDecimal giaDaGiam;
    private BigDecimal tong;
    private int trangThai;
    private HoaDon hoaDon;
    private SanPhamChiTiet sanPhamChiTiet;

    public HoaDonChiTietResponse(HoaDonChiTiet hoaDonChiTiet) {
        this.id = hoaDonChiTiet.getId();
        this.soLuong = hoaDonChiTiet.getSoLuong();
        this.giaHienHanh = hoaDonChiTiet.getGiaHienHanh();
        this.giaDaGiam = hoaDonChiTiet.getGiaDaGiam();
        this.tong = hoaDonChiTiet.getGiaDaGiam().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong()));
        this.trangThai = hoaDonChiTiet.getTrangThai();
        this.hoaDon = hoaDonChiTiet.getHoaDon();
        this.sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaHienHanh() {
        return giaHienHanh;
    }

    public void setGiaHienHanh(BigDecimal giaHienHanh) {
        this.giaHienHanh = giaHienHanh;
    }

    public BigDecimal getGiaDaGiam() {
        return giaDaGiam;
    }

    public void setGiaDaGiam(BigDecimal giaDaGiam) {
        this.giaDaGiam = giaDaGiam;
    }

    public BigDecimal getTong() {
        setTong();
        return tong;
    }

    public void setTong() {
        this.tong = getGiaDaGiam().multiply(BigDecimal.valueOf(getSoLuong()));
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public SanPhamChiTiet getSanPhamChiTiet() {
        return sanPhamChiTiet;
    }

    public void setSanPhamChiTiet(SanPhamChiTiet sanPhamChiTiet) {
        this.sanPhamChiTiet = sanPhamChiTiet;
    }

}
