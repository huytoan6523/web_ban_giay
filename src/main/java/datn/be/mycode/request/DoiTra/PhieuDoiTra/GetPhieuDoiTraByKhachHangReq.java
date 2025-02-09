package datn.be.mycode.request.DoiTra.PhieuDoiTra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GetPhieuDoiTraByKhachHangReq {

    private Integer page;

    private Integer pageSize;

    private Long idKhachHang;

}
