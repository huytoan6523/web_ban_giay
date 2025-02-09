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

public class KiemTraHangReq {
    private Long idPhieuDoiTra;
    private Long idNhanVien;
    private List<String> hinhAnhList;
}
