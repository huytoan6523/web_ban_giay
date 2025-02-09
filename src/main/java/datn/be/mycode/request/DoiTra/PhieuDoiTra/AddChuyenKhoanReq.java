package datn.be.mycode.request.DoiTra.PhieuDoiTra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddChuyenKhoanReq {

    private Long idPhieuDoiTra;
    private String soTaiKhoanNganHang;
    private String tenChuTaiKhoan;
    private String tenNganHang;
}
