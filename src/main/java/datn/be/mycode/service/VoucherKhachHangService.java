package datn.be.mycode.service;

import datn.be.mycode.entity.Voucher;
import datn.be.mycode.entity.VoucherKhachHang;
import datn.be.mycode.repository.KhachHangRepository;
import datn.be.mycode.repository.VoucherKhachHangRepository;
import datn.be.mycode.repository.VoucherRepository;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.VoucherKhachHangRequest;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.VoucherKhachHangResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherKhachHangService {
    @Autowired
    VoucherKhachHangRepository voucherKhachHangRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    KhachHangRepository khachHangRepository;

    public NormalTableResponse<VoucherKhachHangResponse> getAll(NormalTableRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<VoucherKhachHangResponse> voucherKhachHangNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<VoucherKhachHangResponse> voucherKhachHangPage;
        if (request.getKeyWord() != null) {
            voucherKhachHangPage = voucherKhachHangRepository.searchVoucherByKhachHangMa(request.getKeyWord(), pageable);
        }else {
            voucherKhachHangPage = voucherKhachHangRepository.findAllByTrangThai(request.getStatus(), pageable);
        }

        List<VoucherKhachHangResponse> voucherKhachHangResponses = voucherKhachHangPage.getContent().stream().toList();

        voucherKhachHangNormalTableResponse.setItem(voucherKhachHangResponses);
        voucherKhachHangNormalTableResponse.setPage(request.getPage());
        voucherKhachHangNormalTableResponse.setPageSize(request.getPageSize());
        voucherKhachHangNormalTableResponse.setTotalItem(voucherKhachHangPage.getTotalElements());

        return voucherKhachHangNormalTableResponse;
    }


    public VoucherKhachHang add(VoucherKhachHangRequest request) {
        System.out.println("addso1");
        System.out.println(request);
        VoucherKhachHang voucherKhachHang = new VoucherKhachHang();
        voucherKhachHang.setKhachHang(khachHangRepository.searchById(request.getKhachHang()).get());
        voucherKhachHang.setVoucher(voucherRepository.searchById(request.getVoucher()).get());
        voucherKhachHang.setKeySuKien(request.getKeySuKien());
        voucherKhachHang.setTrangThai(1);
        voucherKhachHang.setNgayTao(LocalDateTime.now());
        return voucherKhachHangRepository.save(voucherKhachHang);
    }


    public VoucherKhachHang update(VoucherKhachHangRequest request) {
        VoucherKhachHang voucherKhachHang = voucherKhachHangRepository.findById(request.getId()).get();
        voucherKhachHang.setKhachHang(khachHangRepository.searchById(request.getKhachHang()).get());
        voucherKhachHang.setVoucher(voucherRepository.searchById(request.getVoucher()).get());
        voucherKhachHang.setKeySuKien(request.getKeySuKien());
        voucherKhachHang.setTrangThai(request.getTrangThai());
        voucherKhachHang.setNgaySua(LocalDateTime.now());
        return voucherKhachHangRepository.save(voucherKhachHang);
    }


    public VoucherKhachHang udateTrangThai(Long id, Integer status){
        VoucherKhachHang voucherKhachHang = voucherKhachHangRepository.findById(id).get();
        voucherKhachHang.setTrangThai(status);
        return voucherKhachHangRepository.save(voucherKhachHang);
    }


    public List<VoucherKhachHang> getVouchersByKhachHangId(Long idKhachHang) {
        var voucherKhachHangs = voucherKhachHangRepository.findAllByKhachHangId(idKhachHang);
//        List<Voucher> vouchers = new ArrayList<>();
//        for (VoucherKhachHang voucherKhachHang:voucherKhachHangs) {
//            vouchers.add(voucherKhachHang.getVoucher());
//        }
        return voucherKhachHangs;
    }
}
