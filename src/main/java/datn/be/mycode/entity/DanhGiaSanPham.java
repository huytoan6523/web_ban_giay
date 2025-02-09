package datn.be.mycode.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "danh_gia_san_pham")
public class DanhGiaSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sao")
    private Integer sao;

    @Column(name = "binh_luan", columnDefinition = "nvarchar(max)")
    private String binhLuan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;


    @OneToMany(mappedBy = "danhGiaSanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HinhAnhBinhLuan> hinhAnhBinhLuan = new ArrayList<>();

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang idKhachHang;

    @ManyToOne()
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    private SanPham idSanPham;

}
