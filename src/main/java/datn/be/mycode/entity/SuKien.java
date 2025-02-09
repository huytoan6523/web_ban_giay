package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "su_kien")
public class SuKien implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_su_kien", columnDefinition = "nvarchar(max)")
    private String tenSuKien;

    @Column(name = "ma_su_kien")
    private String maSuKien;

    @Column(name = "hinh_anh", columnDefinition = "nvarchar(max)")
    private String hinhAnh;

    @Column(name = "thang_hoat_dong")
    private String thangHoatDong;

    @Column(name = "don_vi_phieu_giam_gia")
    private int donViPhieuGiamGia;

    @Column(name = "thoi_gian_het_han_phieu")
    private int thoiGianHetHanPhieu;

    @Column(name = "key_su_kien")
    private String keySuKien;

    @Column(name = "mo_ta", columnDefinition = "nvarchar(max)")
    private String moTa;

    @Column(name = "trang_thai")
    private int trangThai;
}
