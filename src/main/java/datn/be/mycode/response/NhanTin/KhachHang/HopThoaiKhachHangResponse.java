package datn.be.mycode.response.NhanTin.KhachHang;

import datn.be.mycode.entity.DoanChatKhachHang;
import datn.be.mycode.entity.HopThoaiKhachHang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HopThoaiKhachHangResponse {
    private HopThoaiKhachHang hopThoaiKhachHang;
    private DoanChatKhachHang doanChatCuoi;
}
