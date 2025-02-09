package datn.be.mycode.request.gioHangChiTIet;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddGioHangChiTietReq {

    private int soLuong;
    private Long idKhachhang;
    private Long idSanPhamChiTiet;

}
