package datn.be.mycode.response.DoiTra;

import datn.be.mycode.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DetailPhieuDoiTraRes {
    private PhieuDoiTra phieuDoiTra;
    private List<SanPhamDoiTra> sanPhamDoiTraList;
    private List<ThaoTacPhieuDoiTra> thaoTacPhieuDoiTraList;
    private List<ThongTinLyDoDoiTra> khachHangDoiTraThongTinList;
    private List<ThongTinKiemTra> thongTinKiemTraList;
    private List<ThongTinKiemTra> thongTinCuaHangChuyenKhoanList;
}
