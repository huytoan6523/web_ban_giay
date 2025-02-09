package datn.be.mycode.request;

import datn.be.mycode.entity.SuKien;
import datn.be.mycode.entity.Voucher;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChiPhieuGGSKRequest {
    private Long id;
    private String keys;
    private int trangThai;
    private Long suKien;
    private Long voucher;
}
