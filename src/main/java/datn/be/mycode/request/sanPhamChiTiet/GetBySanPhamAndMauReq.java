package datn.be.mycode.request.sanPhamChiTiet;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetBySanPhamAndMauReq {

    private Long idSanPham;
    private Long idMau;
}
