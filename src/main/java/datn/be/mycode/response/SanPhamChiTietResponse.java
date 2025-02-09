package datn.be.mycode.response;

import datn.be.mycode.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SanPhamChiTietResponse {

    private Long id;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private String hinhAnh;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private int soLuong;
    private int trangThai;
    private SanPham sanPham;
    private MauSac mauSac;
    private KichCo kichCo;


}
