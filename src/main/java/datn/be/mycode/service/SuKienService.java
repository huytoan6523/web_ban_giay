package datn.be.mycode.service;

import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.SuKien;
import datn.be.mycode.entity.ThongBao;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.SuKien.SuKienRequest;
import datn.be.mycode.request.SuKien.SuKienUpdateReq;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.SuKien.ThangActive;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SuKienService {
    @Autowired
    SuKienRepository suKienRepository;
    @Autowired
    private ChiPhieuGiamGiaSuKienRepository chiPhieuGiamGiaSuKienRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private KhachHangRepository khachHangRepository;

    public NormalTableResponse<SuKien> getAll(TableLongRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        loadSuKien();

        NormalTableResponse<SuKien> suKienNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<SuKien> suKienPage;

        if (request.getKeyWord() != null) {
            suKienPage = suKienRepository.findAllById(request.getKeyWord(), pageable);
        } else if (request.getStatus() != null){
            suKienPage = suKienRepository.findAllByTrangThai(request.getStatus(), pageable);
        }else {
            suKienPage = suKienRepository.findAll( pageable);
        }

        List<SuKien> suKiens = suKienPage.getContent().stream().toList();

        suKienNormalTableResponse.setItem(suKiens);
        suKienNormalTableResponse.setPage(request.getPage());
        suKienNormalTableResponse.setPageSize(request.getPageSize());
        suKienNormalTableResponse.setTotalItem(suKienPage.getTotalElements());

        return suKienNormalTableResponse;
    }


    public ResponseEntity<?> add(SuKienRequest request) {
        LocalDateTime now = LocalDateTime.now();
        var donViPhieuGiamGia = request.getDonViPhieuGiamGia();
        if(donViPhieuGiamGia > 2000) return getErro("Đợn vị phiếu giảm giá không quá 2000");
        if(donViPhieuGiamGia <= 0 ) return getErro("Đợn vị phiếu giảm giá phải lơn hơn 0");
        var thoiGianHetHanPhieu = request.getThoiGianHetHanPhieu();
        if(thoiGianHetHanPhieu > 30) return getErro("Thời gian hết hạn phiếu giảm giá không quá 30");
        if(thoiGianHetHanPhieu <= 0) return getErro("Thời gian hết hạn phiếu giảm giá phải lơn hơn 0");

        if(request.getTenSuKien().isBlank()) return getErro("Không để trống tên");
        if(request.getDonViPhieuGiamGia() == null) return getErro("Không để trống số lượng mỗi loại phiếu giảm giá");
        if(request.getThoiGianHetHanPhieu() == null) return getErro("Không để trống thời gian hết hạn phiếu");

        var thangHoatDong = request.getThangHoatDong().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        if(thangHoatDong.isBlank())return getErro("Cần chọn tháng cho sự kiện");

        // Kiểm tra tháng bắt đầu sự kiện, tháng cuối phải sau tháng hiện tại

        SuKien suKien = new SuKien();
        suKien.setTenSuKien(request.getTenSuKien());
        suKien.setThangHoatDong(thangHoatDong);
        suKien.setDonViPhieuGiamGia(donViPhieuGiamGia);// <= 2000
        suKien.setThoiGianHetHanPhieu(thoiGianHetHanPhieu);// <= 30
        suKien.setHinhAnh(request.getHinhAnh());
        suKien.setMoTa(request.getMoTa());
        suKien.setTrangThai(0);

        SuKien savedSuKien = suKienRepository.save(suKien);
        savedSuKien.setMaSuKien("SK"+savedSuKien.getId());
        savedSuKien.setKeySuKien(savedSuKien.getMaSuKien()+"-"+now.getMonth().getValue()+"-"+now.getYear());

        loadSuKien();

        return ResponseEntity.ok(suKienRepository.save(savedSuKien));
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

    public ResponseEntity<?> update(SuKienUpdateReq request) {
        LocalDateTime now = LocalDateTime.now();
        var donViPhieuGiamGia = request.getDonViPhieuGiamGia();
        if(donViPhieuGiamGia > 2000) return getErro("Đợn vị phiếu giảm giá không quá 2000");
        if(donViPhieuGiamGia <= 0 ) return getErro("Đợn vị phiếu giảm giá phải lơn hơn 0");
        var thoiGianHetHanPhieu = request.getThoiGianHetHanPhieu();
        if(thoiGianHetHanPhieu > 30) return getErro("Thời gian hết hạn phiếu giảm giá không quá 30");
        if(thoiGianHetHanPhieu <= 0) return getErro("Thời gian hết hạn phiếu giảm giá phải lơn hơn 0");

        if(request.getTenSuKien().isBlank()) return getErro("Không để trống tên");
        if(request.getDonViPhieuGiamGia() == null) return getErro("Không để trống số lượng mỗi loại phiếu giảm giá");
        if(request.getThoiGianHetHanPhieu() == null) return getErro("Không để trống thời gian hết hạn phiếu");

        var thangHoatDong = request.getThangHoatDong().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        if(thangHoatDong.isBlank())return getErro("Cần chọn tháng cho sự kiện");

        // Kiểm tra tháng bắt đầu sự kiện, tháng cuối phải sau tháng hiện tại

        SuKien suKien = suKienRepository.findById(request.getId()).orElse(null);
        if(suKien == null) return getErro("Sự kiện này không có");

        suKien.setTenSuKien(request.getTenSuKien());
        suKien.setThangHoatDong(thangHoatDong);
        suKien.setDonViPhieuGiamGia(donViPhieuGiamGia);// <= 2000
        suKien.setThoiGianHetHanPhieu(thoiGianHetHanPhieu);// <= 30
        suKien.setHinhAnh(request.getHinhAnh());
        suKien.setMoTa(request.getMoTa());
//        suKien.setTrangThai(0);

        SuKien savedSuKien = suKienRepository.save(suKien);

        loadSuKien();

        return ResponseEntity.ok(suKienRepository.save(savedSuKien));
    }


    public SuKien udateTrangThai(Long id, Integer trangThai){
        SuKien suKien = suKienRepository.findById(id).get();
        suKien.setTrangThai(trangThai);
        return suKienRepository.save(suKien);
    }

    public Object getThangActive() {
        loadSuKien();
        List<ThangActive> thangActives = new ArrayList<>();

        var sukiens = suKienRepository.findAllWithActiveMonths();
        Set<Integer> activeMonths = new HashSet<>();
        for (SuKien suKien:sukiens) {
            String[] monthsArray = suKien.getThangHoatDong().split(",");
            for (String month : monthsArray) {
                activeMonths.add(Integer.parseInt(month.trim()));
            }
        }
        for (int i = 1; i <= 12; i++) {
            thangActives.add(new ThangActive(i, activeMonths.contains(i))); // Kiểm tra xem tháng có trong Set không
        }
        return thangActives;
    }

    public ResponseEntity<?> getDetailSuKien(Long idSuKien) {
        var suKien = suKienRepository.findById(idSuKien).orElse(null);
        if(suKien == null) return getErro("Sự kiện này không tồn tại");

        return ResponseEntity.ok(suKien);
    }

    public void loadSuKien(){
//        VDthangsng = "1,2,7"
//                thì 1,2 cùng key
//                7 là key khác
//
//                trừ sô lượng ở voucher mõi khi thay dỏ

//        khac su kien khong thay doi
//        khac key tao

        var now = LocalDateTime.now();
        var suKiens = suKienRepository.findAllWithActiveMonths();

        for (SuKien suKien : suKiens) {
            List<Integer> activeMonths = new ArrayList<>();
            String[] monthsArray = suKien.getThangHoatDong().split(",");
            for (String month : monthsArray) {
                activeMonths.add(Integer.parseInt(month.trim()));
            }

            if(activeMonths.contains(now.getMonth().getValue()) && suKien.getTrangThai() == 1) return;
            if(!activeMonths.contains(now.getMonth().getValue()) && suKien.getTrangThai() == 1){
                suKien.setTrangThai(0);
                setVoucher(suKien);
            }

            if(activeMonths.contains(now.getMonth().getValue()) && suKien.getTrangThai() == 0){
                var keySuKien = suKien.getMaSuKien()+"-"+now.getMonth().getValue()+"-"+now.getYear();
                suKien.setTrangThai(1);
                suKien.setKeySuKien(keySuKien);

                setVoucher(suKien);
                guiThongBao(suKien);
            }
            suKienRepository.save(suKien);
        }

    }

    private void guiThongBao(SuKien suKien) {
        var khachHangList = khachHangRepository.findAll();
        var khachHangEmail = khachHangList.stream().filter(khachHang -> khachHang.getEmail() != null).collect(Collectors.toList());

        khachHangEmail.forEach(khachHang -> {
            ThongBao thongBao = new ThongBao();
            thongBao.setNoiDung("Có sự kiện mới, nhanh tay vào mua hàng");
            thongBao.setUrl("/g4/shop/su-kien-thang");
            thongBao.setDaDoc(false);
            thongBao.setNgayTao(LocalDateTime.now());
            thongBao.setTrangThai(1);
            thongBao.setKhachHang(khachHang);

            thongBaoRepository.save(thongBao);
            messagingTemplate.convertAndSend("/topic/notifications", khachHang.getId());
            //email
        });

    }

    private void setVoucher(SuKien suKien) {
        var chiPhieuSuKiens = chiPhieuGiamGiaSuKienRepository.findAllBySuKienId(suKien.getId());
//        if (chiPhieuSuKiens.isEmpty()) return;

        if (suKien.getTrangThai()==0){
            for (ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien : chiPhieuSuKiens) {
                chiPhieuGiamGiaSuKien.setTrangThai(0);
                chiPhieuGiamGiaSuKienRepository.save(chiPhieuGiamGiaSuKien);
                var voucher = chiPhieuGiamGiaSuKien.getVoucher();
                voucher.setSoLuong(0);
                voucherRepository.save(voucher);
            }
        }
        if (suKien.getTrangThai() == 1){
            for (ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien : chiPhieuSuKiens) {
                chiPhieuGiamGiaSuKien.setKeys(suKien.getKeySuKien());
                chiPhieuGiamGiaSuKien.setTrangThai(1);
                chiPhieuGiamGiaSuKienRepository.save(chiPhieuGiamGiaSuKien);
                var voucher = chiPhieuGiamGiaSuKien.getVoucher();
                voucher.setSoLuong(suKien.getDonViPhieuGiamGia());
                voucherRepository.save(voucher);
            }
        }

    }

    public Object getSuKienActive() {
        var now = LocalDateTime.now();
        var suKiens = suKienRepository.findAllWithActiveMonths();
        for (SuKien suKien : suKiens) {
            if(suKien.getTrangThai() == 1) return ResponseEntity.ok(suKien);
        }
        return getErro("Không có sự kiện nào trong tháng này");
    }
}
