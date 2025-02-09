package datn.be.mycode.request.SuKien;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SuKienRequest {

    private String tenSuKien;
    private List<Integer> thangHoatDong;
    private String hinhAnh;
    private String moTa;
    private Integer donViPhieuGiamGia;
    private Integer thoiGianHetHanPhieu;

}
