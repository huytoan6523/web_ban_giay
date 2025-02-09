package datn.be.mycode.service;

import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.entity.ThuongHieu;
import datn.be.mycode.repository.ThuongHieuRepository;

import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.ThuongHieuRequest;
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
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository repository;

    public NormalTableResponse<ThuongHieu> getAll(NormalTableRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<ThuongHieu> thuongHieuNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<ThuongHieu> thuongHieuPage;
        if (request.getKeyWord() != null) {
            thuongHieuPage = repository.searchByTen(request.getKeyWord(), pageable);
        }else {
            thuongHieuPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }

        List<ThuongHieu> thuongHieus = thuongHieuPage.getContent().stream().toList();

        thuongHieuNormalTableResponse.setItem(thuongHieus);
        thuongHieuNormalTableResponse.setPage(request.getPage());
        thuongHieuNormalTableResponse.setPageSize(request.getPageSize());
        thuongHieuNormalTableResponse.setTotalItem(thuongHieuPage.getTotalElements());

        return thuongHieuNormalTableResponse;
    }

    public ResponseEntity<?> add(String request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request).orElse(null);
        if (checkName != null) return getErro("Tên này đã tồn tại");


        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setTen(request);
        thuongHieu.setTrangThai(1);
        thuongHieu.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(thuongHieu));
    }



    public ResponseEntity<?> update(ThuongHieuRequest request) {
        String erroMess = check(request.getTen());
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request.getTen()).orElse(null);

        ThuongHieu thuongHieu = repository.findById(request.getId()).get();
        if (checkName != null && checkName.getId() != thuongHieu.getId()) return getErro("Tên này đã tồn tại");
        thuongHieu.setTen(request.getTen());
        thuongHieu.setTrangThai(request.getTrangThai());
        thuongHieu.setNgaySua(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(thuongHieu));
    }

    private String check(String request) {
        if(request==null ||request.isBlank()) return "Bạn chưa nhập dữ liệu";
        return "";
    }

//    public void delete(ThuongHieu thuongHieu) {
//        repository.delete(thuongHieu);
//    }
//
//
//    public ThuongHieu deleteById(Long id) {
//        ThuongHieu thuongHieu = repository.findById(id).get();
//        thuongHieu.setTrangThai(0);
//        return repository.save(thuongHieu);
//    }

    public ThuongHieu udateTrangThai(Long id, Integer status){
        ThuongHieu thuongHieu = repository.findById(id).get();
        thuongHieu.setNgaySua(LocalDateTime.now());
        thuongHieu.setTrangThai(status);
        return repository.save(thuongHieu);
    }
    public List<ThuongHieu> findAllThuongHieu() {
        return repository.findAllByTrangThai(1);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
