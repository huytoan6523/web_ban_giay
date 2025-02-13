package datn.be.mycode.response.ChiPhieuSuKien;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SuKienRes {
    private Long id;
    private String tenSuKien;
    private String maSuKien;
    private List<Thang> thangHoatDong;
    private int donViPhieuGiamGia;
    private int thoiGianHetHanPhieu;
    private String keySuKien;
    private int trangThai;
}
