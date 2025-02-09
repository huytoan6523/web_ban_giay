package datn.be.mycode.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "hinh_anh_binh_luan")
public class HinhAnhBinhLuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anh", columnDefinition = "nvarchar(max)")
    private String anh;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_danh_gia_san_pham", referencedColumnName = "id")
    @JsonBackReference
    private DanhGiaSanPham danhGiaSanPham;

}
