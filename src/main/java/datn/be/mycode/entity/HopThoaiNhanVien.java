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
@Table(name = "hop_thoai_nhan_vien")
public class HopThoaiNhanVien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tin_nhan_cuoi",columnDefinition = "NVARCHAR(MAX)")
    private String tinNhanCuoi;

    @Column(name = "thoi_gian_nhan_cuoi")
    private LocalDateTime thoiGianNhanCuoi;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_nhan_vien_1", referencedColumnName = "id")
    private NhanVien nhanVien1;

    @ManyToOne()
    @JoinColumn(name = "id_nhan_vien_2", referencedColumnName = "id")
    private NhanVien nhanVien2;

}
