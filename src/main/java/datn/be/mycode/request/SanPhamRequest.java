package datn.be.mycode.request;

import datn.be.mycode.entity.SanPham;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SanPhamRequest {
    private Long id;
    private String ma;
    private String ten;
    private String moTa;
    private String hinhAnh;
    private int trongLuong;
    private String beMatSuDung;
    private String congNghe;
    private String kieuDang;
    private LocalDateTime ngayTao;
    private LocalDateTime NgaySua;
    private String nguoiTao;
    private int trangThai;

    private Long idGiamGia;
    private Long idThuongHieu;
    private Long idTheLoai;
    private Long idLoaiCo;
    private Long idVatLieu;
    private Long idLoaiDe;

    public void setSanPhamRequest(SanPham sanPham){
        this.id = sanPham.getId();
        this.ma = sanPham.getMa();
        this.ten = sanPham.getTen();
        this.moTa = sanPham.getMoTa();
        this.hinhAnh = sanPham.getHinhAnh();
        this.trongLuong = sanPham.getTrongLuong();
        this.beMatSuDung = sanPham.getBeMatSuDung();
        this.congNghe = sanPham.getCongNghe();
        this.kieuDang = sanPham.getKieuDang();
        this.ngayTao = sanPham.getNgayTao();
        this.nguoiTao = sanPham.getNguoiTao();
        this.trangThai = sanPham.getTrangThai();
        this.idGiamGia = sanPham.getGiamGia() == null ? Long.valueOf(0):sanPham.getGiamGia().getId();
        this.idThuongHieu = sanPham.getThuongHieu().getId();
        this.idTheLoai = sanPham.getTheLoai().getId();
        this.idLoaiCo = sanPham.getLoaiCo().getId();
        this.idVatLieu = sanPham.getVatLieu().getId();
        this.idLoaiDe = sanPham.getLoaiDe().getId();
    }
}