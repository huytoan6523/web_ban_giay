package datn.be.mycode.service;

import datn.be.mycode.entity.DoanChatKhachHang;
import datn.be.mycode.entity.DoanChatNhanVien;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.NhanTin.KhachHang.DoanChat.AddDoanChatKhachHang;
import datn.be.mycode.request.NhanTin.KhachHang.DoanChat.GetByHopThoaiKhachHang;
import datn.be.mycode.request.NhanTin.NhanVien.DoanChat.AddDoanChat;
import datn.be.mycode.response.NhanTin.KhachHang.DoanChat.DoanChatKhachHangGet;
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
public class DoanChatKhachHangService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private HopThoaiKhachHangRepository hopThoaiKhachHangRepository;
    @Autowired
    private DoanChatKhachHangRepository doanChatKhachHangRepository;

    public ResponseEntity<?> addDoanChatKhachHang(AddDoanChatKhachHang request){

        var khachHang = khachHangRepository.findById(request.getIdKhachHang()).orElse(null);
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if(khachHang == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdKhachHang());
        if(nhanVien == null) return getErro("Không tìm thấy nhân viên có mã: "+ request.getIdNhanVien());
        var hopThoaiKhachHang = hopThoaiKhachHangRepository.findById(request.getIdHopThoaiKhachHang()).orElse(null);
        if(hopThoaiKhachHang == null) getErro("hộp thoại này không tồn tại");

        String hinhAnh = "";
        if(!request.getHinhAnhList().isEmpty()){
            hinhAnh = String.join(",", request.getHinhAnhList());
        }

        hopThoaiKhachHang.setThoiGianNhanCuoi(LocalDateTime.now());
        hopThoaiKhachHang.setTrangThai(2);
        hopThoaiKhachHangRepository.save(hopThoaiKhachHang);

        DoanChatKhachHang doanChatKhachHang = new DoanChatKhachHang();
        doanChatKhachHang.setTheLoai(request.getTheLoai());
        doanChatKhachHang.setHinhAnh(hinhAnh);
        doanChatKhachHang.setVoice(request.getVoice());
        doanChatKhachHang.setTinNhan(request.getTinNhan());
        doanChatKhachHang.setThoiGian(LocalDateTime.now());
        doanChatKhachHang.setHuongGui(request.getHuongGui());
        doanChatKhachHang.setKhachHang(khachHang);
        doanChatKhachHang.setNhanVien(nhanVien);
        doanChatKhachHang.setHopThoaiKhachHang(hopThoaiKhachHang);
        doanChatKhachHang.setTrangThai(1);

        return ResponseEntity.ok(doanChatKhachHangRepository.save(doanChatKhachHang));
    }

    public NormalTableResponse<DoanChatKhachHangGet> getDoanChatKhachHangByHopThoai(GetByHopThoaiKhachHang request){
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());

        var doanChatByHopThoaiList = doanChatKhachHangRepository.findAllByHopThoaiKhachHangIdOrderByThoiGianDesc(request.getIdHopThoai(), pageable);
        if(doanChatByHopThoaiList.isEmpty()) getErro("hộp thoại này chua có chat");

        List<DoanChatKhachHangGet> doanChatKhachHangGets = new ArrayList<>();
        doanChatByHopThoaiList.forEach(doanChatNhanVien -> {
            List<String> hinhAnh = null;
            if(doanChatNhanVien.getHinhAnh()!= null){
            hinhAnh = Arrays.stream(doanChatNhanVien.getHinhAnh().split(",")).toList();
            }

            DoanChatKhachHangGet doanChatGet = new DoanChatKhachHangGet();
            doanChatGet.setId(doanChatNhanVien.getId());
            doanChatGet.setTheLoai(doanChatNhanVien.getTheLoai());
            doanChatGet.setHinhAnhList(hinhAnh);
            doanChatGet.setNgayGui(doanChatNhanVien.getThoiGian());
            doanChatGet.setVoice(doanChatNhanVien.getVoice());
            doanChatGet.setTinNhan(doanChatNhanVien.getTinNhan());
            doanChatGet.setHuongGui(doanChatNhanVien.getHuongGui());
            doanChatGet.setKhachHang(doanChatNhanVien.getKhachHang());
            doanChatGet.setNhanVien(doanChatNhanVien.getNhanVien());
            doanChatGet.setHopThoaiKhachHang(doanChatGet.getHopThoaiKhachHang());
            doanChatKhachHangGets.add(doanChatGet);

        });
        var hoaDonNormalTableResponse = new NormalTableResponse<DoanChatKhachHangGet>();
        hoaDonNormalTableResponse.setItem(doanChatKhachHangGets);
        hoaDonNormalTableResponse.setPage(request.getPage());
        hoaDonNormalTableResponse.setPageSize(request.getPageSize());
        hoaDonNormalTableResponse.setTotalItem(doanChatByHopThoaiList.getTotalElements());
        return hoaDonNormalTableResponse;
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
