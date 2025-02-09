package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "khach_hang_doi_tra_thong_tin")
public class ThongTinLyDoDoiTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", columnDefinition = "varchar(max)")
    private String video;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_phieu_doi_tra", referencedColumnName = "id")
    private PhieuDoiTra phieuDoiTra;
}
