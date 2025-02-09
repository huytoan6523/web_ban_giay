package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.LichSuSuDung.LichSuSuDungRequest;
import datn.be.mycode.request.LichSuSuDung.LichSuSuDungUpdateRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.response.LichSuSuDung.VoucherTrangThaiSuDung;
import datn.be.mycode.response.NormalTableResponse;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class LichSuSuDungService {
    @Autowired
    LichSuSuDungRepository repository;
    @Autowired
    ChiPhieuGiamGiaSuKienRepository chiPhieuRepository;
    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    private SuKienRepository suKienRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherKhachHangRepository voucherKhachHangRepository;
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public NormalTableResponse<LichSuSuDung> getAll(TableLongRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        NormalTableResponse<LichSuSuDung> lichSuSuDungNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<LichSuSuDung> lichSuSuDungPage;

        if (request.getKeyWord() != null) {
            lichSuSuDungPage = repository.findAllById(request.getKeyWord(), pageable);
        }else if (request.getStatus() != null){
            lichSuSuDungPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }else {
            lichSuSuDungPage = repository.findAll( pageable);
        }

        List<LichSuSuDung> lichSuSuDungs = lichSuSuDungPage.getContent().stream().toList();

        lichSuSuDungNormalTableResponse.setItem(lichSuSuDungs);
        lichSuSuDungNormalTableResponse.setPage(request.getPage());
        lichSuSuDungNormalTableResponse.setPageSize(request.getPageSize());
        lichSuSuDungNormalTableResponse.setTotalItem(lichSuSuDungPage.getTotalElements());

        return lichSuSuDungNormalTableResponse;
    }


    public Object add(LichSuSuDungRequest request) {

        var chiPhieuGiamGiaSuKienieu = chiPhieuRepository.searchById(request.getChiPhieuGiamGiaSuKien()).get();
        var suKien = chiPhieuGiamGiaSuKienieu.getSuKien();
        var khachHang = khachHangRepository.searchById(request.getKhachHang()).get();
        var voucher = chiPhieuGiamGiaSuKienieu.getVoucher();
        var now = LocalDateTime.now();
        var ngayKetThuc = now.plusDays(suKien.getThoiGianHetHanPhieu());
        var soLuong = voucher.getSoLuong() - 1;
        if(soLuong < 0) return getErro("voucher này đã hết không thêm được");

        voucher.setSoLuong(soLuong);
        voucherRepository.save(voucher);

//        thêm voucher vào voucherKhachHang
        VoucherKhachHang voucherKhachHang = new VoucherKhachHang();
        voucherKhachHang.setNgayTao(now);
        voucherKhachHang.setNgayBatDau(now);
        voucherKhachHang.setNgayKetThuc(ngayKetThuc);
        voucherKhachHang.setKeySuKien(chiPhieuGiamGiaSuKienieu.getKeys());
        voucherKhachHang.setSoLuong(1);
        voucherKhachHang.setTrangThai(1);
        voucherKhachHang.setVoucher(voucher);
        voucherKhachHang.setKhachHang(khachHang);
        voucherKhachHangRepository.save(voucherKhachHang);

        ThongBao(voucher, khachHang);

        LichSuSuDung lichSuSuDung = new LichSuSuDung();
        lichSuSuDung.setNgayTao(now);
        lichSuSuDung.setKeys(chiPhieuGiamGiaSuKienieu.getKeys());
        lichSuSuDung.setChiPhieuGiamGiaSuKien(chiPhieuGiamGiaSuKienieu);
        lichSuSuDung.setKhachHang(khachHang);
        lichSuSuDung.setTrangThai(1);
        return ResponseEntity.ok(repository.save(lichSuSuDung));
    }

    private void ThongBao(Voucher voucher, KhachHang khachHang) {
        ThongBao thongBao = new ThongBao();
        thongBao.setNoiDung("Bạn đã thêm thành công voucher "+voucher.getTen());
        thongBao.setUrl("/g4/shop/khach-hang?activeItem=2");
        thongBao.setDaDoc(false);
        thongBao.setNgayTao(LocalDateTime.now());
        thongBao.setTrangThai(1);
        thongBao.setKhachHang(khachHang);

        thongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/notifications", khachHang.getId());
    }


    public LichSuSuDung update(LichSuSuDungUpdateRequest request) {
        LichSuSuDung lichSuSuDung = repository.findById(request.getId()).get();
        lichSuSuDung.setChiPhieuGiamGiaSuKien(chiPhieuRepository.searchById(request.getChiPhieuGiamGiaSuKien()).get());
        lichSuSuDung.setKhachHang(khachHangRepository.searchById(request.getKhachHang()).get());
//        lichSuSuDung.setTrangThai(request.getTrangThai());
        return repository.save(lichSuSuDung);
    }

    public LichSuSuDung udateTrangThai(Long id, Integer trangThai){
        LichSuSuDung lichSuSuDung = repository.findById(id).get();
        lichSuSuDung.setTrangThai(trangThai);
        return repository.save(lichSuSuDung);
    }

    public Object getTrangThaiSuDungVoucher(Long idKhachHang) {

        var suKien = suKienRepository.findByTrangThai(1).orElse(null);
        if(suKien == null) return getErro("Không có sự kiện nào trong tháng này");
        var chiPhieus = chiPhieuRepository.findAllBySuKienId(suKien.getId());
        List<VoucherTrangThaiSuDung> respone = new ArrayList<>();

        for (ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien :chiPhieus) {
            var voucher = chiPhieuGiamGiaSuKien.getVoucher();
            var lichSuSuDungs = repository.findAllByChiPhieuGiamGiaSuKienIdAndKhachHangId(chiPhieuGiamGiaSuKien.getId(), idKhachHang);
            var lichSuSuDungSuKienBayGio = lichSuSuDungs.stream()
                    .filter(lichSuSuDung -> lichSuSuDung.getKeys() != null && lichSuSuDung.getKeys().equals(chiPhieuGiamGiaSuKien.getKeys()))
                    .findAny().orElse(null);
            VoucherTrangThaiSuDung voucherTrangThaiSuDung = new VoucherTrangThaiSuDung();
            voucherTrangThaiSuDung.setVoucher(voucher);
            voucherTrangThaiSuDung.setChiPhieuGiamGiaSuKien(chiPhieuGiamGiaSuKien);
            voucherTrangThaiSuDung.setCustumerUser(false); //chưa dùng
            if(lichSuSuDungSuKienBayGio != null) {
                voucherTrangThaiSuDung.setCustumerUser(true);
            }

            respone.add(voucherTrangThaiSuDung);
        }

        return ResponseEntity.ok(respone);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
