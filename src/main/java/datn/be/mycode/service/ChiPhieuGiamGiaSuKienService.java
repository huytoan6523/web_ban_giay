package datn.be.mycode.service;

import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.SuKien;
import datn.be.mycode.entity.Voucher;
import datn.be.mycode.repository.ChiPhieuGiamGiaSuKienRepository;
import datn.be.mycode.repository.SuKienRepository;
import datn.be.mycode.repository.VoucherRepository;
import datn.be.mycode.request.ChiPhieuGGSKRequest;
import datn.be.mycode.request.ChiPhieuGiamGia.AddChiPheuGiamGia;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.response.ChiPhieuSuKien.ChiPhieuSuKienDetail;
import datn.be.mycode.response.ChiPhieuSuKien.SuKienRes;
import datn.be.mycode.response.ChiPhieuSuKien.Thang;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.SuKien.ThangActive;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChiPhieuGiamGiaSuKienService {
    @Autowired
    ChiPhieuGiamGiaSuKienRepository cprepository;
    @Autowired
    SuKienRepository suKienRepository;
    @Autowired
    VoucherRepository voucherRepository;

    public NormalTableResponse<ChiPhieuGiamGiaSuKien> getAll(TableLongRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        NormalTableResponse<ChiPhieuGiamGiaSuKien> chiPhieuNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<ChiPhieuGiamGiaSuKien> chiPhieuGiamGiaSuKienPage;

        if (request.getKeyWord() != null) {
            chiPhieuGiamGiaSuKienPage = cprepository.findAllById(request.getKeyWord(), pageable);
        }else if (request.getStatus() != null){
            chiPhieuGiamGiaSuKienPage = cprepository.findAllByTrangThai(request.getStatus(), pageable);
        }else {
            chiPhieuGiamGiaSuKienPage = cprepository.findAll( pageable);
        }

        List<ChiPhieuGiamGiaSuKien> chiPhieuGiamGiaSuKiens = chiPhieuGiamGiaSuKienPage.getContent().stream().toList();

        chiPhieuNormalTableResponse.setItem(chiPhieuGiamGiaSuKiens);
        chiPhieuNormalTableResponse.setPage(request.getPage());
        chiPhieuNormalTableResponse.setPageSize(request.getPageSize());
        chiPhieuNormalTableResponse.setTotalItem(chiPhieuGiamGiaSuKienPage.getTotalElements());

        return chiPhieuNormalTableResponse;
    }


    public Object add(AddChiPheuGiamGia request) {
        System.out.println(request);
        var voucher = voucherRepository.searchById(request.getVoucher()).get();
        var suKien = suKienRepository.searchById(request.getSuKien()).get();
        var chiPhieuGiamGiaSuKien = cprepository.findBySuKienIdAndVoucherId(request.getSuKien(),request.getVoucher());
        if(chiPhieuGiamGiaSuKien.size() >1) return getErro("Voucher này đã có trong sự kiện");

        ChiPhieuGiamGiaSuKien cpggsk = new ChiPhieuGiamGiaSuKien();
        cpggsk.setKeys(suKien.getKeySuKien());
        cpggsk.setSuKien(suKien);
        cpggsk.setVoucher(voucher);
        cpggsk.setTrangThai(1);
        return cprepository.save(cpggsk);
    }


    public ChiPhieuGiamGiaSuKien update(ChiPhieuGGSKRequest request) {
        ChiPhieuGiamGiaSuKien cpggsk = cprepository.findById(request.getId()).get();
        cpggsk.setKeys(request.getKeys());
        cpggsk.setSuKien(suKienRepository.searchById(request.getSuKien()).get());
        cpggsk.setVoucher(voucherRepository.searchById(request.getVoucher()).get());
        cpggsk.setTrangThai(request.getTrangThai());
        return cprepository.save(cpggsk);
    }

    public ChiPhieuGiamGiaSuKien udateTrangThai(Long id, Integer trangThai){
        ChiPhieuGiamGiaSuKien cpggsk = cprepository.findById(id).get();
        cpggsk.setTrangThai(trangThai);
        return cprepository.save(cpggsk);
    }

    public Object getDetailByIdSuKien(Long idSuKien) {
        var cpsks = cprepository.findAllBySuKienId(idSuKien);
        var suKien = suKienRepository.findById(idSuKien).get();
        String thangHoatDong = suKien.getThangHoatDong();

        List<Voucher> vouchers = new ArrayList<>();
        for (ChiPhieuGiamGiaSuKien chiPhieuGiamGiaSuKien:cpsks) {
            vouchers.add(chiPhieuGiamGiaSuKien.getVoucher());
        }

        return vouchers;
    }
//
//    public Object loadSuKien() {
//        var now = LocalDateTime.now();
//
//        var vouchers = voucherRepository.findAllByTheLoai(2);
//        var sukiens = suKienRepository.findAllWithActiveMonths();
//        for (SuKien suKien:sukiens) {
//                String[] monthsArray = suKien.getThangHoatDong().split(",");
//                for (String month : monthsArray) {
//                    if(month = now.getMonth())
//                    return suKien;
//                }
//            }
//
//        return "";
//    }
//    public Object getThangActive() {
//        List<ThangActive> thangActives = new ArrayList<>();
//
//        var sukiens = suKienRepository.findAllWithActiveMonths();
//        Set<Integer> activeMonths = new HashSet<>();
//        for (SuKien suKien:sukiens) {
//            String[] monthsArray = suKien.getThangHoatDong().split(",");
//            for (String month : monthsArray) {
//                activeMonths.add(Integer.parseInt(month.trim()));
//            }
//        }
//        for (int i = 1; i <= 12; i++) {
//            thangActives.add(new ThangActive(i, activeMonths.contains(i))); // Kiểm tra xem tháng có trong Set không
//        }
//
//        return thangActives;
//    }
private ResponseEntity<?> getErro(String s){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
}
}
