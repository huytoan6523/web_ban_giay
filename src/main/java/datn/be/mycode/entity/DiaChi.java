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
@Table(name = "dia_chi")
public class DiaChi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tinh_thanh", columnDefinition = "nvarchar(max)")
    private String idTinhThanh;
    @Column(name = "tinh_thanh", columnDefinition = "nvarchar(max)")
    private String tinhThanh;

    @Column(name = "id_quan_huyen", columnDefinition = "nvarchar(max)")
    private String idQuanHuyen;
    @Column(name = "quan_huyen", columnDefinition = "nvarchar(max)")
    private String quanHuyen;

    @Column(name = "id_phuong_xa", columnDefinition = "nvarchar(max)")
    private String idPhuongXa;
    @Column(name = "phuong_xa", columnDefinition = "nvarchar(max)")
    private String phuongXa;

    @Column(name = "ghi_chu", columnDefinition = "nvarchar(max)")
    private String ghiChu;

    @Column(name = "trang_thai")
    private int trangThai;

    @Column(name = "mac_dinh")
    private boolean macDinh;

    @ManyToOne()
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;

}
