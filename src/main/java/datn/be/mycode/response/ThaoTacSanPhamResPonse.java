package datn.be.mycode.response;

import datn.be.mycode.entity.NhanVien;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThaoTacSanPhamResPonse {
    private Long id;

    private String thaoTac;
    private LocalDateTime NgaySua;
    private int trangThai;
private NhanVien nhanVien;
}
