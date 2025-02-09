package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name = "san_pham")
public class SanPham implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten", columnDefinition = "nvarchar(max)")
    private String ten;

    @Column(name = "mo_ta", columnDefinition = "nvarchar(max)")
    private String moTa;

    @Column(name = "hinh_anh", columnDefinition = "nvarchar(max)")
    private String hinhAnh;

    @Column(name = "trong_luong")
    private int trongLuong;

    @Column(name = "be_mat_su_dung", columnDefinition = "nvarchar(max)")
    private String beMatSuDung;

    @Column(name = "cong_nghe", columnDefinition = "nvarchar(max)")
    private String congNghe;

    @Column(name = "kieu_dang", columnDefinition = "nvarchar(max)")
    private String kieuDang;

    @Column(name = "gia_thap_nhat")
    private BigDecimal giaThapNhat;

    @Column(name = "gia_cao_nhat")
    private BigDecimal giaCaoNhat;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "trung_binh_danh_gia")
    private Integer trungBinhDanhGia;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "trang_thai")
    private int trangThai;


    @ManyToOne()
    @JoinColumn(name = "id_giam_gia", referencedColumnName = "id")
    private GiamGia giamGia;

    @ManyToOne()
    @JoinColumn(name = "id_thuong_hieu", referencedColumnName = "id")
    private ThuongHieu thuongHieu;

    @ManyToOne()
    @JoinColumn(name = "id_the_loai", referencedColumnName = "id")
    private TheLoai theLoai;

    @ManyToOne()
    @JoinColumn(name = "id_loai_co", referencedColumnName = "id")
    private LoaiCo loaiCo;

    @ManyToOne()
    @JoinColumn(name = "id_vat_lieu", referencedColumnName = "id")
    private VatLieu vatLieu;

    @ManyToOne()
    @JoinColumn(name = "id_loai_de", referencedColumnName = "id")
    private LoaiDe loaiDe;

    public SanPham(Long id) {
        this.id = id;
    }
}
