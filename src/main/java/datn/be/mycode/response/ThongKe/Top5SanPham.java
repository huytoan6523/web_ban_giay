package datn.be.mycode.response.ThongKe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Top5SanPham {
    private String tenSanPham;
    private int soLuong;
}
