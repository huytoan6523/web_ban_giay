package datn.be.mycode.request.DiaChi;

import datn.be.mycode.entity.KhachHang;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiaChiRequest {

    private Long id;
    @NotBlank(message = "ko dc trong")
    private String idTinhThanh;
    private String tinhThanh;
    private String idQuanHuyen;
    private String quanHuyen;
    private String idPhuongXa;
    private String phuongXa;
    private String ghiChu;
//    private Boolean macDinh;
    private int trangThai;
    private Long idKhachHang;
}
