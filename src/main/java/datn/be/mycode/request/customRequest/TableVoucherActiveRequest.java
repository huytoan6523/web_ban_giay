package datn.be.mycode.request.customRequest;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TableVoucherActiveRequest {
    private Integer page;

    private Integer pageSize;

    private BigDecimal giaTriGiamToiThieu;

    private Integer theLoai;
}
