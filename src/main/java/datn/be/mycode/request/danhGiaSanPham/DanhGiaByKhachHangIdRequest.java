package datn.be.mycode.request.danhGiaSanPham;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DanhGiaByKhachHangIdRequest {

    private Integer page;

    private Integer pageSize;

    private Long idKhachHang;

    private Integer status;
}
