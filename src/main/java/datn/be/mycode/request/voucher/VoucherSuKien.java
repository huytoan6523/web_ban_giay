package datn.be.mycode.request.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class VoucherSuKien {

    private String ten;
    private Integer theLoai;
    private Integer phanTramGiam;
    private BigDecimal giamToiDa;
    private BigDecimal giaTriGiamToiThieu;
}
