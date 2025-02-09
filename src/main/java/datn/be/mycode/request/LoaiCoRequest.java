package datn.be.mycode.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LoaiCoRequest {

    private Long id;
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
