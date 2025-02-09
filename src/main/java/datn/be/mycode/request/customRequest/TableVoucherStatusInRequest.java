package datn.be.mycode.request.customRequest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TableVoucherStatusInRequest {
    private Integer page;

    private Integer pageSize;

    private Integer theLoai;
}
