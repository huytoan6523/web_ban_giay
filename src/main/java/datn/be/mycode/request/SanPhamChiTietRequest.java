package datn.be.mycode.request;

import datn.be.mycode.entity.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SanPhamChiTietRequest {

    private Long id;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private String hinhAnh;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private int soLuong;
    private int trangThai;
    private Long sanPham;
    private Long mauSac;
    private Long kichCo;
}
