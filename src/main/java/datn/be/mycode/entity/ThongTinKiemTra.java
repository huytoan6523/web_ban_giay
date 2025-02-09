package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "nhan_vien_thao_tac_kiem_tra")
public class ThongTinKiemTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video", columnDefinition = "varchar(max)")
    private String video;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_phieu_doi_tra", referencedColumnName = "id")
    private PhieuDoiTra phieuDoiTra;
}
