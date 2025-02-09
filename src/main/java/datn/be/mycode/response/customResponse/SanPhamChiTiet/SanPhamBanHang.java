package datn.be.mycode.response.customResponse.SanPhamChiTiet;

import datn.be.mycode.entity.SanPham;
import datn.be.mycode.entity.SanPhamChiTiet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPhamBanHang {
    SanPham sanPham;
    List<SanPhamChiTiet> sanPhamChiTiets;
}
