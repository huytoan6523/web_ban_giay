package datn.be.mycode.request.khachHang;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateKhachHangRequest {
    private Long id;
    private String ma;
    private String hoTen;
    private String hinhAnh;
    private LocalDateTime ngaySinh;
//    private Date ngayTao;
    private int gioiTinh;
}
