package datn.be.mycode.request.customRequest;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TableVoucherRequest {
    private Integer page;

    private Integer pageSize;

    private String keyWord;

    private Integer status;

    private Integer theLoai;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer phanTramGiam;


}
