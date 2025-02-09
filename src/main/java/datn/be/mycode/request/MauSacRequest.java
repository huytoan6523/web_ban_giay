package datn.be.mycode.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class MauSacRequest {

    private Long id;
    @NotBlank(message = "ko dc trong")
    private String ten;
    private String maMau;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
