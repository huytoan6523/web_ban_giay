package datn.be.mycode.request.customRequest.BanHang;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddDiaChi {
    private Long idHoaDon;
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
