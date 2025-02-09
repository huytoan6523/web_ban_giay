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
@Table(name = "lich_su_su_dung")
public class LichSuSuDung implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "key_su_kien")
    private String keys;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_chi_phieu_giam_gia_su_kien",referencedColumnName = "id")
    private ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;
}
