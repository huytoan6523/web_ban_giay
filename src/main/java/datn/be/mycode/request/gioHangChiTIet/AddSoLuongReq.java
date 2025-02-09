package datn.be.mycode.request.gioHangChiTIet;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddSoLuongReq {

    private Long idGioHangChiTiet;
    private int soLuong;
}
