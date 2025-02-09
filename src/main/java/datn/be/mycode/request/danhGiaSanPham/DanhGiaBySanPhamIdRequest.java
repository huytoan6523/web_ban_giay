package datn.be.mycode.request.danhGiaSanPham;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DanhGiaBySanPhamIdRequest {
    private Integer page;

    private Integer pageSize;

    private Long idSanPham;

    private Integer status;
}
