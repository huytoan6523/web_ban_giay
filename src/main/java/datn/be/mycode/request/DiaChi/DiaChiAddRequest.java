package datn.be.mycode.request.DiaChi;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiaChiAddRequest {

    private Long id;
    @NotBlank(message = "ko dc trong")
    private String idTinhThanh;
    private String tinhThanh;
    private String idQuanHuyen;
    private String quanHuyen;
    private String idPhuongXa;
    private String phuongXa;
    private String ghiChu;
    private int trangThai;
    private Long idKhachHang;
}
