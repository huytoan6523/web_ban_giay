package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VoucherHoaDonRequest {
    private Long id;
    @NotBlank(message = "ko dc trong")
    private LocalDateTime ngayTao;
    private LocalDateTime ngaySua;
    private int trangThai;
    private Long voucher;
    private Long hoaDon;

    public VoucherHoaDonRequest(Long voucher, Long hoaDon) {
        this.voucher = voucher;
        this.hoaDon = hoaDon;
        this.trangThai = 1;
    }
}
