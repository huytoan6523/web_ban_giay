package datn.be.mycode.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VoucherRequest {
    private Long id;
    @NotBlank(message = "ko dc trong")
    private String ma;
    private String ten;
    private int theLoai;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private int soLuong;
    private int phanTramGiam;
    private BigDecimal giamToiDa;
    private BigDecimal giaTriGiamToiThieu;
    private int trangThai;
}
