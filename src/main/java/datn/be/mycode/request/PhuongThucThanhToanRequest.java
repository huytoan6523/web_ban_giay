package datn.be.mycode.request;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PhuongThucThanhToanRequest {
    private Long id;
    private String ten;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;
}
