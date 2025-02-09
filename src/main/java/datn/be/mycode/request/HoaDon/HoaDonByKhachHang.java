package datn.be.mycode.request.HoaDon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HoaDonByKhachHang {

    private Integer page;

    private Integer pageSize;

    private Long idKhachHang;
}
