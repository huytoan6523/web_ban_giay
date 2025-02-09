package datn.be.mycode.response.DoiTra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTrangThaiRes {

    private String thaoTac;

    private Long idPhieuDoiTra;

    private Long idNhanVien;
}
