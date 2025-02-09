package datn.be.mycode.response.gioHangChiTiet;

import datn.be.mycode.entity.GioHang;
import datn.be.mycode.entity.GioHangChiTiet;
import datn.be.mycode.entity.SanPhamChiTiet;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AddGioHangChiTietRes {

    private Long id;
    private int soLuong;
    private BigDecimal giaHienHanh;
    private BigDecimal giaDaGiam;
    private int trangThai;
    private GioHang gioHang;
    private SanPhamChiTiet sanPhamChiTiet;

    public AddGioHangChiTietRes(GioHangChiTiet gioHangChiTiet) {
        this.id = gioHangChiTiet.getId();
        this.soLuong = gioHangChiTiet.getSoLuong();
        this.giaHienHanh = gioHangChiTiet.getGiaHienHanh();
        this.giaDaGiam = gioHangChiTiet.getGiaDaGiam();
        this.trangThai = gioHangChiTiet.getTrangThai();
        this.gioHang = gioHangChiTiet.getGioHang();
        this.sanPhamChiTiet = gioHangChiTiet.getSanPhamChiTiet();
    }
}
