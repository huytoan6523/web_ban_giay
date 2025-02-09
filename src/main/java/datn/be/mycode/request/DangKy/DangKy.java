package datn.be.mycode.request.DangKy;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class DangKy {
    private Long id;
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Tài khoản không được để trống")
    @Schema(description = "Tài khoản đăng nhập của người dùng", example = "username123", required = true)

    private String taiKhoan;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Schema(description = "Mật khẩu của người dùng", example = "123456", required = true)

    private String tenNguoiDung;
    private String SDT;
    private String matKhau;
    private String confirmPassword;
    private Date ngayTao;
}
