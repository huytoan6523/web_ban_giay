package datn.be.mycode.response.ChiPhieuSuKien;

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
public class ChiPhieuSuKienDetail {

    private Long id;
    private String keys;
    private int trangThai;
    private SuKienRes suKien;
    private Voucher voucher;

}
