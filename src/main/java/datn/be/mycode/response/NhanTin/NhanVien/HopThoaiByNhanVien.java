package datn.be.mycode.response.NhanTin.NhanVien;

import datn.be.mycode.entity.DoanChatNhanVien;
import datn.be.mycode.entity.HopThoaiNhanVien;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HopThoaiByNhanVien {
    private HopThoaiNhanVien hopThoaiNhanVien;
    private DoanChatNhanVien doanChatCuoi;
}
