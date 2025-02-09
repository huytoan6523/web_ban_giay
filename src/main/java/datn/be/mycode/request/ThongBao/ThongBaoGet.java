package datn.be.mycode.request.ThongBao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThongBaoGet {

    private Integer page;

    private Integer pageSize;

    private Long idKhachHang;
}
