package datn.be.mycode.request.NhanTin.KhachHang.DoanChat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddDoanChatKhachHang {

    private String theLoai;
    private List<String> hinhAnhList;
    private String voice;
    private String tinNhan;
    private Long idKhachHang;
    private Long idNhanVien;
    private String huongGui;
    private Long idHopThoaiKhachHang;
}
