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
@ToString

@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_voucher")
    private String ma;

    @Column(name = "ten_voucher", columnDefinition = "nvarchar(max)")
    private String ten;

    @Column(name = "the_loai")
    private int theLoai;

    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "phan_tram_giam")
    private int phanTramGiam;

    @Column(name = "giam_toi_da")
    private BigDecimal giamToiDa;

    @Column(name = "gia_tri_don_toi_thieu")
    private BigDecimal giaTriGiamToiThieu;

    @Column(name = "trang_thai")
    private int trangThai;

}
