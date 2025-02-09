package datn.be.mycode.request.LichSuSuDung;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LichSuSuDungUpdateRequest {
    private Long id;
    private Long chiPhieuGiamGiaSuKien;
    private Long khachHang;

}
