package datn.be.mycode.request.customRequest.BanHang;


import datn.be.mycode.entity.Voucher;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ThanhToanRequest {

    private Long idHoaDon;
    private BigDecimal phiShip;
    private BigDecimal tongTien;
    private BigDecimal tongTienSanPhamChuaGiam;
    private BigDecimal tongTienGiam;
    private int trangThai;

}
