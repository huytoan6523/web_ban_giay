package datn.be.mycode.request.customRequest.BanHang;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeleteVoucher {
    private Long idHoaDon;
    private Long idVoucher;
}
