package datn.be.mycode.response.NhanTin.NhanVien.DoanChat;

import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.entity.NhanVien;
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
public class DoanChatGet {

    private Long id;
    private String theLoai;
    private List<String> hinhAnhList;
    private LocalDateTime ngayGui;
    private String voice;
    private String tinNhan;
    private NhanVien nguoiGui;
    private NhanVien nguoiNhan;
    private HopThoaiNhanVien hopThoaiNhanVien;
}
