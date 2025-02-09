package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "san_pham_doi_tra")
public class SanPhamDoiTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "gia_san_pham")
    private BigDecimal giaSanPham;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_san_pham_doi_tra", referencedColumnName = "id")
    private SanPhamChiTiet sanPham;

    @ManyToOne()
    @JoinColumn(name = "id_phieu_doi_tra", referencedColumnName = "id")
    private PhieuDoiTra phieuDoiTra;
}
