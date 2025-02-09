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
@Table(name = "doan_chat_nhan_vien")
public class DoanChatNhanVien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "the_loai")
    private String theLoai;

    @Column(name = "hinh_anh", columnDefinition = "varchar(max)")
    private String hinhAnh;

    @Column(name = "duong_dan_ghi_am", columnDefinition = "varchar(max)")
    private String voice;

    @Column(name = "tin_nhan", columnDefinition = "nvarchar(max)")
    private String tinNhan;

    @Column(name = "thoi_gian")
    private LocalDateTime thoiGian;

    @ManyToOne()
    @JoinColumn(name = "id_nguoi_gui", referencedColumnName = "id")
    private NhanVien nguoiGui;

    @ManyToOne()
    @JoinColumn(name = "id_nguoi_nhan", referencedColumnName = "id")
    private NhanVien nguoiNhan;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_hop_thoai_nhan_vien", referencedColumnName = "id")
    private HopThoaiNhanVien hopThoaiNhanVien;

}
