package datn.be.mycode.response.sanPham;

import lombok.*;

import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SanPhamDanhGiaResponse {
    private Long idSanPham;
    private String tenSanPham;
    private Map<Integer, Long> saoThongKe; // {5: 17900, 4: 1400, ...}
    private Long coBinhLuan;
    private Long coHinhAnh;
}
