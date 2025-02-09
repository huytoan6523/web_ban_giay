package datn.be.mycode.request.DangKy;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class DangKyRequest {

    private Long id;

    @NotBlank(message = "Mã không được để trống")
    private String ma;
    private String hinhAnh;
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDateTime ngaySinh;


    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")

    private String soDienThoai;

    @NotNull(message = "Giới tính không được để trống")
    @Min(value = 0, message = "Giới tính không hợp lệ")
    @Max(value = 1, message = "Giới tính không hợp lệ")
    private int gioiTinh;

    @NotNull(message = "Trạng thái không được để trống")
    private int trangThai;
}
