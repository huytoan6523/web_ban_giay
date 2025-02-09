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

@Entity
@Table(name = "khach_hang")
public class KhachHang implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ho_ten", columnDefinition = "nvarchar(max)")
    private String hoTen;

    @Column(name = "hinh_anh", columnDefinition = "nvarchar(max)")
    private String hinhAnh;

    @Column(name = "ngay_sinh")
    private LocalDateTime ngaySinh;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "sdt")
    private String soDienThoai;

    @Column(name = "gioi_tinh")
    private int gioiTinh;

    @Column(name = "email")
    private String email;

    @Column(name = "tai_khoan")
    private String taiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "trang_thai")
    private int trangThai;

    public KhachHang(Long id) {
        this.id = id;
    }
}
