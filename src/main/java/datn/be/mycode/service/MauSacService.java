package datn.be.mycode.service;


import datn.be.mycode.entity.MauSac;
import datn.be.mycode.repository.MauSacRepository;
import datn.be.mycode.request.MauSacRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MauSacService {

    @Autowired
    private MauSacRepository repository;

    public NormalTableResponse<MauSac> getAll(NormalTableRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<MauSac> mauSacNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<MauSac> mauSacPage;
        if (request.getKeyWord() != null) {
            mauSacPage = repository.searchByTen(request.getKeyWord(), pageable);
        }else {
            mauSacPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }

        List<MauSac> mauSacs = mauSacPage.getContent().stream().toList();

        mauSacNormalTableResponse.setItem(mauSacs);
        mauSacNormalTableResponse.setPage(request.getPage());
        mauSacNormalTableResponse.setPageSize(request.getPageSize());
        mauSacNormalTableResponse.setTotalItem(mauSacPage.getTotalElements());

        return mauSacNormalTableResponse;
    }

    public ResponseEntity<?> add(MauSacRequest request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request.getTen()).orElse(null);
        if (checkName != null) return getErro("Tên này đã tồn tại");

        MauSac mauSac = new MauSac();
        mauSac.setTen(request.getTen());
        mauSac.setMaMau(request.getMaMau());
        mauSac.setTrangThai(1);
        mauSac.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(mauSac));
    }


    public ResponseEntity<?> update(MauSacRequest request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request.getTen()).orElse(null);
        MauSac mauSac = repository.findById(request.getId()).get();
        if (checkName != null && checkName.getId() != mauSac.getId()) return getErro("Tên này đã tồn tại");

        mauSac.setTen(request.getTen());
        mauSac.setMaMau(request.getMaMau());
        mauSac.setTrangThai(request.getTrangThai());
        mauSac.setNgaySua(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(mauSac));
    }

    private String check(MauSacRequest request) {
        if(request.getTen().isBlank()) return "Bạn chưa nhập tên";
        if(request.getMaMau().isBlank()) return "Bạn chưa chọn mã màu";
        return "";
    }

//    public void delete(MauSac mauSac) {
//        repository.delete(mauSac);
//    }
//
//
//    public MauSac deleteById(Long id) {
//        MauSac mauSac = repository.findById(id).get();
//        mauSac.setTrangThai(0);
//        return repository.save(mauSac);
//    }

    public MauSac udateTrangThai(Long id, Integer status){
        MauSac mauSac = repository.findById(id).get();
        mauSac.setNgaySua(LocalDateTime.now());
        mauSac.setTrangThai(status);
        return repository.save(mauSac);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
