package datn.be.mycode.request;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class VatLieuRequest {

    private Long id;
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
