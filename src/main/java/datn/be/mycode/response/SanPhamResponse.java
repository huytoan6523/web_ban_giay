package datn.be.mycode.response;


import datn.be.mycode.entity.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPhamResponse {
    private Long id;
    private String ma;
    private String ten;
    private String moTa;
    private String hinhAnh;
    private int trongLuong;
    private String beMatSuDung;
    private String congNghe;
    private String kieuDang;
    private BigDecimal giaThapNhat;
    private BigDecimal giaCaoNhat;
    private LocalDateTime ngayTao;
    private LocalDateTime NgaySua;
    private String nguoiTao;
    private int soLuong;
    private int trangThai;
    private GiamGia GiamGia;
    private ThuongHieu ThuongHieu;
    private TheLoai TheLoai;
    private LoaiCo LoaiCo;
    private VatLieu VatLieu;
    private LoaiDe LoaiDe;

}
