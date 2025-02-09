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
@Table(name = "doan_chat_khach_hang")
public class DoanChatKhachHang implements Serializable {

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

    @Column(name = "huong_gui")
    private String huongGui;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @Column(name = "trang_thai")
    private int trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_hop_thoai_khach_hang", referencedColumnName = "id")
    private HopThoaiKhachHang hopThoaiKhachHang;

}
