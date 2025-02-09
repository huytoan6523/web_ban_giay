package datn.be.mycode.service;

import datn.be.mycode.entity.HopThoaiNhanVien;
import datn.be.mycode.repository.HopThoaiNhanVienRepository;
import datn.be.mycode.repository.NhanVienRepository;
import datn.be.mycode.request.NhanTin.NhanVien.HopThoaiNhanVien.AddHopThoaiNhanVien;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HopThoaiNhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private HopThoaiNhanVienRepository hopThoaiNhanVienRepository;

    public ResponseEntity<?> addHopThoaiNhanVien(AddHopThoaiNhanVien request){
        if(request.getIdNhanVien1() == null || request.getIdNhanVien2() == null) return getErro("Bạn truyền thiếu id nhan vien");
        var nhanVien1 = nhanVienRepository.findById(request.getIdNhanVien1()).orElse(null);
        var nhanVien2 = nhanVienRepository.findById(request.getIdNhanVien2()).orElse(null);
        if(nhanVien1 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien1());
        if(nhanVien2 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien2());

        var hopThoaiNhanVienCheck = hopThoaiNhanVienRepository.findByNhanViens(nhanVien1.getId(),nhanVien2.getId()).orElse(null);
        if(hopThoaiNhanVienCheck != null) return getErro("Hộp thoại cho 2 nhân viên này đã tồn tại");

        HopThoaiNhanVien hopThoaiNhanVien = new HopThoaiNhanVien();

        hopThoaiNhanVien.setNhanVien1(nhanVien1);
        hopThoaiNhanVien.setNhanVien2(nhanVien2);
        hopThoaiNhanVien.setTrangThai(1);

        return ResponseEntity.ok(hopThoaiNhanVienRepository.save(hopThoaiNhanVien));
    }

    public ResponseEntity<?> checkHopThoaiNhanVien(AddHopThoaiNhanVien request){
        if(request.getIdNhanVien1() == null || request.getIdNhanVien2() == null) return getErro("Bạn truyền thiếu id nhân viên");
        var nhanVien1 = nhanVienRepository.findById(request.getIdNhanVien1()).orElse(null);
        var nhanVien2 = nhanVienRepository.findById(request.getIdNhanVien2()).orElse(null);
        if(nhanVien1 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien1());
        if(nhanVien2 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien2());

        var hopThoaiNhanVien = hopThoaiNhanVienRepository.findByNhanViens(nhanVien1.getId(),nhanVien2.getId()).orElse(null);

        if(hopThoaiNhanVien == null) return ResponseEntity.ok(null);
        return ResponseEntity.ok(hopThoaiNhanVien);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

}
