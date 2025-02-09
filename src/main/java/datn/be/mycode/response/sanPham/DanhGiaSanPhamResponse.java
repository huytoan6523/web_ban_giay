package datn.be.mycode.response.sanPham;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DanhGiaSanPhamResponse {
    private Long idDanhGia;
    private int sao;
    private String binhLuan;
    private List<String> hinhAnh;
}
