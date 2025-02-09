package datn.be.mycode.response.customResponse.SanPhamChiTiet;

import datn.be.mycode.entity.SanPham;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SanPhamChiTietByIdSanPhamRes {

    SanPham sanPham;

    List<SanPhamChiTietByIdSP> sanPhamChiTietList;
}
