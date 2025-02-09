package datn.be.mycode.request.SuKien;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SuKienUpdateReq {

    private Long id;
    private String tenSuKien;
    private List<Integer> thangHoatDong;
    private String hinhAnh;
    private String moTa;
    private Integer donViPhieuGiamGia;
    private Integer thoiGianHetHanPhieu;

}
