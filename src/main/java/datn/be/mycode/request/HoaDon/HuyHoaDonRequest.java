package datn.be.mycode.request.HoaDon;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HuyHoaDonRequest {
    private Long idNhanVien;
    private Long idHoaDon;
    private String GhiChu;
}
