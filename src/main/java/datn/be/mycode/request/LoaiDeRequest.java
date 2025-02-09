package datn.be.mycode.request;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LoaiDeRequest {

    private Long id;
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
