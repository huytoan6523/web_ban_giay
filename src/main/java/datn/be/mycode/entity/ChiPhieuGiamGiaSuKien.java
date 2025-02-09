package datn.be.mycode.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "chi_phieu_giam_gia_su_kien")
public class ChiPhieuGiamGiaSuKien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_su_kien")
    private String keys;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_su_kien",referencedColumnName = "id")
    private SuKien suKien;

    @ManyToOne()
    @JoinColumn(name = "id_voucher", referencedColumnName = "id")
    private Voucher voucher;

}
