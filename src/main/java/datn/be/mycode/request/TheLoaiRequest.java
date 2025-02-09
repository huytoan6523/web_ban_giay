package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class TheLoaiRequest {
    private Long id;
    @NotBlank(message = "ko dc trong")
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
