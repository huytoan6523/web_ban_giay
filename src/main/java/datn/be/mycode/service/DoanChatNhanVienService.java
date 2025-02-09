package datn.be.mycode.service;

import datn.be.mycode.entity.DoanChatNhanVien;
import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.repository.DoanChatNhanVienRepository;
import datn.be.mycode.repository.HopThoaiNhanVienRepository;
import datn.be.mycode.repository.NhanVienRepository;
import datn.be.mycode.request.NhanTin.NhanVien.DoanChat.AddDoanChat;
import datn.be.mycode.request.NhanTin.NhanVien.DoanChat.GetByHopThoaiNhanVien;
import datn.be.mycode.response.NhanTin.NhanVien.DoanChat.DoanChatGet;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DoanChatNhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private HopThoaiNhanVienRepository hopThoaiNhanVienRepository;
    @Autowired
    private DoanChatNhanVienRepository doanChatNhanVienRepository;

    public ResponseEntity<?> addDoanChatNhanVien(AddDoanChat request){

        var nhanVien1 = nhanVienRepository.findById(request.getIdNguoiGui()).orElse(null);
        var nhanVien2 = nhanVienRepository.findById(request.getIdNguoiNhan()).orElse(null);
        if(nhanVien1 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNguoiGui());
        if(nhanVien2 == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNguoiNhan());
        var hopThoaiNhanVien = hopThoaiNhanVienRepository.findById(request.getIdHopThoaiNhanVien()).orElse(null);
        if(hopThoaiNhanVien == null) getErro("hộp thoại này không tồn tại");

        String hinhAnh = "";
        if(!request.getHinhAnhList().isEmpty()){
            hinhAnh = String.join(",", request.getHinhAnhList());
        }

        hopThoaiNhanVien.setThoiGianNhanCuoi(LocalDateTime.now());
        hopThoaiNhanVienRepository.save(hopThoaiNhanVien);

        DoanChatNhanVien doanChatNhanVien = new DoanChatNhanVien();
        doanChatNhanVien.setTheLoai(request.getTheLoai());
        doanChatNhanVien.setHinhAnh(hinhAnh);
        doanChatNhanVien.setVoice(request.getVoice());
        doanChatNhanVien.setTinNhan(request.getTinNhan());
        doanChatNhanVien.setThoiGian(LocalDateTime.now());
        doanChatNhanVien.setNguoiGui(nhanVien1);
        doanChatNhanVien.setNguoiNhan(nhanVien2);
        doanChatNhanVien.setHopThoaiNhanVien(hopThoaiNhanVien);
        doanChatNhanVien.setTrangThai(1);

        return ResponseEntity.ok(doanChatNhanVienRepository.save(doanChatNhanVien));
    }

    public NormalTableResponse<DoanChatGet> getDoanChatNhanVienByHopThoai(GetByHopThoaiNhanVien request){
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());

        var doanChatByHopThoaiList = doanChatNhanVienRepository.findAllByHopThoaiNhanVienIdOrderByThoiGianDesc(request.getIdHopThoai(), pageable);
        if(doanChatByHopThoaiList.isEmpty()) getErro("hộp thoại này chua có chat");

        List<DoanChatGet> doanChatGets = new ArrayList<>();
        doanChatByHopThoaiList.forEach(doanChatNhanVien -> {
            List<String> hinhAnh = null;
            if(doanChatNhanVien.getHinhAnh()!= null){
            hinhAnh = Arrays.stream(doanChatNhanVien.getHinhAnh().split(",")).toList();
            }

            DoanChatGet doanChatGet = new DoanChatGet();
            doanChatGet.setId(doanChatNhanVien.getId());
            doanChatGet.setTheLoai(doanChatNhanVien.getTheLoai());
            doanChatGet.setHinhAnhList(hinhAnh);
            doanChatGet.setNgayGui(doanChatNhanVien.getThoiGian());
            doanChatGet.setVoice(doanChatNhanVien.getVoice());
            doanChatGet.setTinNhan(doanChatNhanVien.getTinNhan());
            doanChatGet.setNguoiGui(doanChatNhanVien.getNguoiGui());
            doanChatGet.setNguoiNhan(doanChatNhanVien.getNguoiNhan());
            doanChatGet.setHopThoaiNhanVien(doanChatGet.getHopThoaiNhanVien());
            doanChatGets.add(doanChatGet);

        });
        var hoaDonNormalTableResponse = new NormalTableResponse<DoanChatGet>();
        hoaDonNormalTableResponse.setItem(doanChatGets);
        hoaDonNormalTableResponse.setPage(request.getPage());
        hoaDonNormalTableResponse.setPageSize(request.getPageSize());
        hoaDonNormalTableResponse.setTotalItem(doanChatByHopThoaiList.getTotalElements());
        return hoaDonNormalTableResponse;
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
