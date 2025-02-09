package datn.be.mycode.request.customRequest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TableDiaChiKhachHangRequest {
    private Integer page;

    private Integer pageSize;

    private Long idKhachHang;

}
