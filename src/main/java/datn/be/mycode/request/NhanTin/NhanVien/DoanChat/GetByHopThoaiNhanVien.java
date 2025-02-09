package datn.be.mycode.request.NhanTin.NhanVien.DoanChat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetByHopThoaiNhanVien {

    private Integer page;

    private Integer pageSize;

    private Long idHopThoai;
}
