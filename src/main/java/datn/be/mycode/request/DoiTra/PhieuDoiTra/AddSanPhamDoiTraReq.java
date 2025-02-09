package datn.be.mycode.request.DoiTra.PhieuDoiTra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddSanPhamDoiTraReq {

    private Long idHoaDonChiTiet;
    private Integer soLuong;

}
