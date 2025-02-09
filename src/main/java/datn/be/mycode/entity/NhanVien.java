package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "nhan_vien")
public class NhanVien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ho_ten", columnDefinition = "nvarchar(max)")
    private String hoTen;

    @Column(name = "ngay_sinh")
    private LocalDateTime ngaySinh;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "sdt")
    private String soDienThoai;

    @Column(name = "gioi_tinh", columnDefinition = "nvarchar(10)")
    private int gioiTinh;

    @Column(name = "dia_chi", columnDefinition = "nvarchar(max)")
    private String diaChi;

    @Column(name = "hinh_anh", columnDefinition = "nvarchar(max)")
    private String hinhAnh;

    @Column(name = "email")
    private String email;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_chuc_vu", referencedColumnName = "id")
    private ChucVu chucVu;

}
