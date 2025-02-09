package datn.be.mycode.response.DoiTra;

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
public class KhachHangGetHoaDonChiTietRes {

    private HoaDon hoaDon;

    private List<HoaDonChiTietDoiTraRes> hoaDonChiTietList;

    private List<ThaoTacHoaDon> thaoTacHoaDons;
}
