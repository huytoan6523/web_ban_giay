package datn.be.mycode.response.customResponse.SanPhamChiTiet;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.MauSac;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class SanPhamChiTietByIdSP {

    private Long id;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private String hinhAnh;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private int soLuong;
    private int trangThai;
    private Long sanPham;
    private MauSac mauSac;
    private KichCo kichCo;
}
