package datn.be.mycode.response.NhanTin.KhachHang.DoanChat;

import datn.be.mycode.entity.HopThoaiKhachHang;
import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.entity.NhanVien;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoanChatKhachHangGet {

    private Long id;
    private String theLoai;
    private List<String> hinhAnhList;
    private LocalDateTime ngayGui;
    private String voice;
    private String tinNhan;
    private String huongGui;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private HopThoaiKhachHang hopThoaiKhachHang;
}
