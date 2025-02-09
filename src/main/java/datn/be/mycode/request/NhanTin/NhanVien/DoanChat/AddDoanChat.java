package datn.be.mycode.request.NhanTin.NhanVien.DoanChat;

import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.entity.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddDoanChat {

    private String theLoai;
    private List<String> hinhAnhList;
    private String voice;
    private String tinNhan;
    private Long idNguoiGui;
    private Long idNguoiNhan;
    private Long idHopThoaiNhanVien;
}
