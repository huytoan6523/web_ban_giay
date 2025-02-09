package datn.be.mycode.service;

import datn.be.mycode.entity.HopThoaiKhachHang;
import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.NhanTin.KhachHang.HopThoai.AddHopThoaiKhachHang;
import datn.be.mycode.request.NhanTin.NhanVien.HopThoaiNhanVien.AddHopThoaiNhanVien;
import datn.be.mycode.response.NhanTin.KhachHang.HopThoaiKhachHangResponse;
import datn.be.mycode.response.NhanTin.TrangThai;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HopThoaiKhachHangService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private HopThoaiKhachHangRepository hopThoaiKhachHangRepository;
    @Autowired
    private DoanChatKhachHangRepository doanChatKhachHangRepository;


    public ResponseEntity<?> addHopThoaiKhachHang(AddHopThoaiKhachHang request) {
        var khachHang = khachHangRepository.findById(request.getIdKhachHang()).orElse(null);
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if(khachHang == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdKhachHang());
        if(nhanVien == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien());
        var hopThoaiKhachHangCheck = hopThoaiKhachHangRepository.findByNhanVienIdAndAndKhachHangId(nhanVien.getId(),khachHang.getId()).orElse(null);
        if(hopThoaiKhachHangCheck != null) return getErro("Đã tồn tại hộp thoại khách hàng với nhân viên này");
        HopThoaiKhachHang hopThoaiKhachHang = new HopThoaiKhachHang();

        hopThoaiKhachHang.setKhachHang(khachHang);
        hopThoaiKhachHang.setNhanVien(nhanVien);
        hopThoaiKhachHang.setTrangThai(1);

        return ResponseEntity.ok(hopThoaiKhachHangRepository.save(hopThoaiKhachHang));
    }

    public Object getHopThoaiKhachHangByIdNhanVienAndKeyword(Long idNhanVien, String keyWord) {
        var nhanVien = nhanVienRepository.findById(idNhanVien).orElse(null);
        if(nhanVien == null) return null;
//        if(keyWord.isBlank()) return null;
        var hopThoaiKhachHangs = hopThoaiKhachHangRepository.findByIdNhanVienAndKeyword(idNhanVien,keyWord);
        if (hopThoaiKhachHangs.isEmpty()) return null;
        List<HopThoaiKhachHangResponse> response = new ArrayList<>();
        hopThoaiKhachHangs.forEach(hopThoaiKhachHang -> {
            var doanChatCuoi = doanChatKhachHangRepository.findNewestMessageByHopThoai(hopThoaiKhachHang.getId()).orElse(null);

            HopThoaiKhachHangResponse hopThoaiKhachHangResponse = new HopThoaiKhachHangResponse();
            hopThoaiKhachHangResponse.setHopThoaiKhachHang(hopThoaiKhachHang);
            hopThoaiKhachHangResponse.setDoanChatCuoi(doanChatCuoi);

            response.add(hopThoaiKhachHangResponse);
        });
        return response;
    }

    public Object updateTrangThaiByIdHoiThoaiKhachHang(TrangThai request) {
        var hopThoaiKhachHang = hopThoaiKhachHangRepository.findById(request.getIdHoiThoai()).orElse(null);
        if (hopThoaiKhachHang == null) return "Thất bại";
        if (request.getTrangThai() == null) return "Thất bại";

        hopThoaiKhachHang.setTrangThai(request.getTrangThai());
        hopThoaiKhachHangRepository.save(hopThoaiKhachHang);
        return "Thành công";

    }

    public Object checkHopThoaiKhachHang(Long idNhanVien, Long idKhachHang) {
        if(idNhanVien == null || idKhachHang == null) return getErro("Bạn truyền thiếu id");
        var nhanVien = nhanVienRepository.findById(idNhanVien).orElse(null);
        var khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
        if(nhanVien == null) return getErro("Không tìm thấy nhân viên có mã: "+ idNhanVien);
        if(khachHang == null) return getErro("Không tìm thấy khách hàng có mã: "+ idKhachHang);

        var hopThoaiKhachHang = hopThoaiKhachHangRepository.findByNhanVienIdAndAndKhachHangId(nhanVien.getId(),khachHang.getId()).orElse(null);

        if(hopThoaiKhachHang == null) return ResponseEntity.ok(null);
        return ResponseEntity.ok(hopThoaiKhachHang);
    }

    public Object getHopThoaiKhachHangByIdKhachHang(Long idKhachHang) {
        var khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
        if(khachHang == null) return null;
//        if(keyWord.isBlank()) return null;
        var hopThoaiKhachHangs = hopThoaiKhachHangRepository.findAllByKhachHangIdOrderByThoiGianNhanCuoiDesc(khachHang.getId());
        if (hopThoaiKhachHangs.isEmpty()) return null;
        List<HopThoaiKhachHangResponse> response = new ArrayList<>();
        hopThoaiKhachHangs.forEach(hopThoaiKhachHang -> {
            var doanChatCuoi = doanChatKhachHangRepository.findNewestMessageByHopThoai(hopThoaiKhachHang.getId()).orElse(null);

            HopThoaiKhachHangResponse hopThoaiKhachHangResponse = new HopThoaiKhachHangResponse();
            hopThoaiKhachHangResponse.setHopThoaiKhachHang(hopThoaiKhachHang);
            hopThoaiKhachHangResponse.setDoanChatCuoi(doanChatCuoi);

            response.add(hopThoaiKhachHangResponse);
        });
        return response;
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

}
