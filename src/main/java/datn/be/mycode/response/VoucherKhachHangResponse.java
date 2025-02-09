package datn.be.mycode.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoucherKhachHangResponse {
    private String tenVoucher;
    private String ma;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;
    private int phanTramGiam;
    private int soLuong;
    private boolean coTheSuDung;
    private LocalDateTime ngayTao;
}
