package datn.be.mycode.request.thaoTacHoaDon;


import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.NhanVien;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ThaoTacHoaDonAddRequest {

    private String thaoTac;

    private Long idHoaDon;

    private Long idNhanVien;

}
