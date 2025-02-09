package datn.be.mycode.request.NhanTin.KhachHang.HopThoai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddHopThoaiKhachHang {
    private Long idKhachHang;
    private Long idNhanVien;
}
