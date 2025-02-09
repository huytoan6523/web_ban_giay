package datn.be.mycode.request.sanPham;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SanPhamByAllReq {
    private Integer page;
    private Integer pageSize;
    private String keyWord;
    private Long idGiamGia;
    private BigDecimal giaCaoNhat;
    private BigDecimal giaThapNhat;
    private Long idThuongHieu;
    private Long idTheLoai;
    private Long idLoaiCo;
    private Long idVatLieu;
    private Long idLoaiDe;
    private Integer sort;
}
