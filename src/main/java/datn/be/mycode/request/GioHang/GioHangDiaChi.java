package datn.be.mycode.request.GioHang;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class GioHangDiaChi {

    private Long idKhachHang;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String idTinhThanh;
    private String tinhThanh;
    private String idQuanHuyen;
    private String quanHuyen;
    private String idPhuongXa;
    private String phuongXa;
    private String ghiChu;
}
