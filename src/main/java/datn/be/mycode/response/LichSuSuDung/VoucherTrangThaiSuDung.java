package datn.be.mycode.response.LichSuSuDung;

import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherTrangThaiSuDung {

    private Voucher voucher;
    private ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien;
    private boolean isCustumerUser;

}
