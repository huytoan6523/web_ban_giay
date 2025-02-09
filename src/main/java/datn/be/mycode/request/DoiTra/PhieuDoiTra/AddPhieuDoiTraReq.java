package datn.be.mycode.request.DoiTra.PhieuDoiTra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddPhieuDoiTraReq {

    private Long idHoaDon;
    private List<AddSanPhamDoiTraReq> sanPhamDoiTraList;
    private List<String> hinhAnhList;
    private String lyDoDoiTra;
//    private String soTaiKhoanNganHang;
//    private String tenChuTaiKhoan;
//    private String tenNganHang;
    private Integer theLoai;
}
