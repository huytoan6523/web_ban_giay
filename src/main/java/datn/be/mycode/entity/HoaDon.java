package datn.be.mycode.entity;

import datn.be.mycode.request.HoaDonRequest;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity
@Table(name = "hoa_don")
public class HoaDon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_thanh_toan",nullable = true)
    private LocalDateTime ngayThanhToan;

    @Column(name = "ghi_chu", columnDefinition = "nvarchar(max)")
    private String ghiChu;

    @Column(name = "ten_nguoi_nhan", columnDefinition = "nvarchar(max)")
    private String tenNguoiNhan;

    @Column(name = "sdt_nguoi_nhan")
    private String sdtNguoiNhan;

    @Column(name = "id_tinh_thanh", columnDefinition = "nvarchar(max)")
    private String idTinhThanh;
    @Column(name = "tinh_thanh", columnDefinition = "nvarchar(max)")
    private String tinhThanh;

    @Column(name = "id_quan_huyen", columnDefinition = "nvarchar(max)")
    private String idQuanHuyen;
    @Column(name = "quan_huyen", columnDefinition = "nvarchar(max)")
    private String quanHuyen;

    @Column(name = "id_phuong_xa", columnDefinition = "nvarchar(max)")
    private String idPhuongXa;
    @Column(name = "phuong_xa", columnDefinition = "nvarchar(max)")
    private String phuongXa;

    @Column(name = "phi_ship")
    private BigDecimal phiShip;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "tong_tien_san_pham_chua_giam")
    private BigDecimal tongTienSanPhamChuaGiam;

    @Column(name = "tong_tien_giam")
    private BigDecimal tongTienGiam;

    @Column(name = "thanh_toan")
    private Boolean thanhToan;

    @Column(name = "giao_hang")
    private Boolean giaoHang;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_phuong_thuc_thanh_toan", referencedColumnName = "id")
    private PhuongThucThanhToan phuongThucThanhToan;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;

    @ManyToOne()
    @JoinColumn(name = "id_nhan_vien", referencedColumnName = "id")
    private NhanVien nhanVien;
}
