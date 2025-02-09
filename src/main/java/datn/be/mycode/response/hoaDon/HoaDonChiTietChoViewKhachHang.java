package datn.be.mycode.response.hoaDon;

import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.entity.ThaoTacHoaDon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietChoViewKhachHang {

    private HoaDon hoaDon;

    private List<HoaDonChiTiet> hoaDonChiTietList;

    private List<ThaoTacHoaDon> thaoTacHoaDons;
}
