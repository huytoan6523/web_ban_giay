package datn.be.mycode.request;


import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.SanPhamChiTiet;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class HoaDonChiTietRequest {

    private Long id;
    private int soLuong;
    private int trangThai;
    private Long idHoaDon;
    private Long idSanPhamChiTiet;

}
