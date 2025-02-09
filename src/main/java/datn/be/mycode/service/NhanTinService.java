package datn.be.mycode.service;

import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.repository.*;
import datn.be.mycode.response.NhanTin.NhanVien.HopThoaiByNhanVien;
import datn.be.mycode.response.NhanTin.TrangThai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NhanTinService {

    @Autowired
    private DoanChatKhachHangRepository doanChatKhachHangRepository;
    @Autowired
    private DoanChatNhanVienRepository doanChatNhanVienRepository;
    @Autowired
    private HopThoaiNhanVienRepository hopThoaiNhanVienRepository;
    @Autowired
    private HopThoaiKhachHangRepository hopThoaiKhachHangRepository;

    public List<HopThoaiByNhanVien> getHopThoaiNhanVienByIdNhanVien(Long idNhanVien, String keyWord){
        var hopThoaiNhanVienList = hopThoaiNhanVienRepository.findByNhanVien(idNhanVien,keyWord);
        List<HopThoaiByNhanVien> hopThoaiByNhanViens = new ArrayList<>();
        if(hopThoaiNhanVienList.isEmpty()) return hopThoaiByNhanViens;
        hopThoaiNhanVienList.forEach(hopThoaiNhanVien -> {
           var doanChatNhanVienMoiNhat = doanChatNhanVienRepository.findNewestMessageByHopThoai(hopThoaiNhanVien.getId()).orElse(null);

               HopThoaiByNhanVien hopThoaiByNhanVien = new HopThoaiByNhanVien(hopThoaiNhanVien,doanChatNhanVienMoiNhat);
               hopThoaiByNhanViens.add(hopThoaiByNhanVien);

        });

        return hopThoaiByNhanViens;
    }

    public HopThoaiNhanVien updateTrangThaiByIdHoiThoaiNhanVien(TrangThai request) {
        var hoiThoaiNhanVien = hopThoaiNhanVienRepository.findById(request.getIdHoiThoai()).orElse(null);
        hoiThoaiNhanVien.setTrangThai(request.getTrangThai());
        return hopThoaiNhanVienRepository.save(hoiThoaiNhanVien);
    }
}
