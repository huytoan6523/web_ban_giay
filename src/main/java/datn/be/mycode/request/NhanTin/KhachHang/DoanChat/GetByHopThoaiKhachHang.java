package datn.be.mycode.request.NhanTin.KhachHang.DoanChat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetByHopThoaiKhachHang {

    private Integer page;

    private Integer pageSize;

    private Long idHopThoai;
}
