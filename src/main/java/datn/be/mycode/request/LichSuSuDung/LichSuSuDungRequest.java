package datn.be.mycode.request.LichSuSuDung;

import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.KhachHang;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LichSuSuDungRequest {
//    private Long id;
//    private LocalDateTime ngayTao;
//    private String keys;
//    private int trangThai;
    private Long chiPhieuGiamGiaSuKien;
    private Long khachHang;

}
