package datn.be.mycode.request.khachHang;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DanhGiaSanPhamChungRequest {

    private int sao;
    private String binhLuan;
    private Long idKhachHang;
    private Long idSanPham;

    private List<String> listHinhAnh;
}
