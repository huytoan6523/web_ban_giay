package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "phieu_doi_tra")
public class PhieuDoiTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_phieu_doi_tra")
    private String ma;

    @Column(name = "so_tai_khoan_ngan_hang")
    private String soTaiKhoanNganHang;

    @Column(name = "ten_ngan_hang", columnDefinition = "nvarchar(max)")
    private String tenNganHang;

    @Column(name = "ten_chu_tai_khoan")
    private String tenChuTaiKhoan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ly_do_doi_tra", columnDefinition = "nvarchar(max)")
    private String lyDoDoiTra;

    @Column(name = "ghi_chu_nhan_vien", columnDefinition = "nvarchar(max)")
    private String ghiChuNhanVien;

    @Column(name = "thao_tac")
    private int thaoTac;

    @Column(name = "the_loai")
    private int theLoai;

    @Column(name = "trang_thai")
    private int trangThai;


    @ManyToOne()
    @JoinColumn(name = "id_hoa_don_cu", referencedColumnName = "id")
    private HoaDon hoaDonCu;

    @ManyToOne()
    @JoinColumn(name = "id_hoa_don_moi", referencedColumnName = "id")
    private HoaDon hoaDonMoi;

    @ManyToOne()
    @JoinColumn(name = "id_nhan_vien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;
}
