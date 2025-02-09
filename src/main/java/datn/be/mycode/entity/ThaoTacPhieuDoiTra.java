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
@Table(name = "thao_tac_phieu_doi_tra")
public class ThaoTacPhieuDoiTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thao_tac", columnDefinition = "nvarchar(50)")
    private String thaoTac;

    @Column(name = "ghi_chu", columnDefinition = "nvarchar(max)")
    private String ghiChu;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_phieu_doi_tra", referencedColumnName = "id")
    private PhieuDoiTra phieuDoiTra;

    @ManyToOne()
    @JoinColumn(name = "id_nhan_vien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;
}
