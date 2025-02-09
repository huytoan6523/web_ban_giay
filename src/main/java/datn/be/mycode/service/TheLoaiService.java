package datn.be.mycode.service;

import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.repository.TheLoaiRepository;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.TheLoaiRequest;
import datn.be.mycode.request.TrangThaiRequest;
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
import java.util.Optional;

@Service
public class TheLoaiService {

    @Autowired
    private TheLoaiRepository repo;

    public NormalTableResponse<TheLoai> getAll(NormalTableRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        NormalTableResponse<TheLoai> theLoaiNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<TheLoai> theLoaiPage;
        if (request.getKeyWord() != null) {
            theLoaiPage = repo.searchByTen(request.getKeyWord(), pageable);
        }else {
            theLoaiPage = repo.findAllByTrangThai(request.getStatus(), pageable);
        }
        List<TheLoai> theLoais = theLoaiPage.getContent().stream().toList();

        theLoaiNormalTableResponse.setItem(theLoais);
        theLoaiNormalTableResponse.setPage(request.getPage());
        theLoaiNormalTableResponse.setPageSize(request.getPageSize());
        theLoaiNormalTableResponse.setTotalItem(theLoaiPage.getTotalElements());

        return theLoaiNormalTableResponse;
    }


    public ResponseEntity<?> add(String request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repo.findByTen(request).orElse(null);
        if (checkName != null) return getErro("Tên này đã tồn tại");

        TheLoai theLoai = new TheLoai();
        theLoai.setTen(request);
        theLoai.setTrangThai(1);
        theLoai.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(repo.save(theLoai));
    }


    public ResponseEntity<?> update(TheLoaiRequest request) {
        String erroMess = check(request.getTen());
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repo.findByTen(request.getTen()).orElse(null);

        TheLoai theLoai = repo.findById(request.getId()).get();
        if (checkName != null && checkName.getId() != theLoai.getId()) return getErro("Tên này đã tồn tại");
        theLoai.setTen(request.getTen());
        theLoai.setTrangThai(request.getTrangThai());
        theLoai.setNgaySua(LocalDateTime.now());
        return ResponseEntity.ok(repo.save(theLoai));
    }

    private String check(String request) {
        if(request==null ||request.isBlank()) return "Bạn chưa nhập dữ liệu";
        return "";
    }

    public void delete(TheLoai theLoai) {
        repo.delete(theLoai);
    }


    public TheLoai deleteById(Long id) {
        TheLoai theLoai = repo.findById(id).get();
        theLoai.setTrangThai(0);
        return repo.save(theLoai);
    }

    public TheLoai udateTrangThai(Long id, Integer status){
        TheLoai theLoai = repo.findById(id).get();
        theLoai.setTrangThai(status);
        return repo.save(theLoai);
    }
    public List<TheLoai> findAllTheLoai() {
        return repo.findAll();
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
