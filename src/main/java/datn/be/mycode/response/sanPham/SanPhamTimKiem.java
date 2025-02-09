package datn.be.mycode.response.sanPham;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamTimKiem {
    private Long idSanPham;
    private String ten;
}
