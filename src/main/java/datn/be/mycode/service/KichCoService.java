package datn.be.mycode.service;

import datn.be.mycode.entity.KichCo;
import datn.be.mycode.repository.KichcoRepository;
import datn.be.mycode.request.KichCoRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.VatLieuRequest;
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
public class KichCoService {

    @Autowired
    private KichcoRepository repository;

    public NormalTableResponse<KichCo> getAll(NormalTableRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<KichCo> kichCoNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<KichCo> kichCoPage;
        if (request.getKeyWord() != null) {
            kichCoPage = repository.searchByTen(request.getKeyWord(), pageable);
        }else {
            kichCoPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }

        List<KichCo> kichCos = kichCoPage.getContent();

        kichCoNormalTableResponse.setItem(kichCos);
        kichCoNormalTableResponse.setPage(request.getPage());
        kichCoNormalTableResponse.setPageSize(request.getPageSize());
        kichCoNormalTableResponse.setTotalItem(kichCoPage.getTotalElements());

        return kichCoNormalTableResponse;
    }

    public ResponseEntity<?> add(String request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request).orElse(null);
        if (checkName != null) return getErro("Tên này đã tồn tại");

        KichCo kichCo = new KichCo();
        kichCo.setTen(request);
        kichCo.setTrangThai(1);
        kichCo.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(kichCo));
    }


    public ResponseEntity<?> update(KichCoRequest request) {
        String erroMess = check(request.getTen());
        if (!erroMess.isBlank()) return getErro(erroMess);
        var checkName = repository.findByTen(request.getTen()).orElse(null);

        KichCo kichCo = repository.findById(request.getId()).get();
        if (checkName != null && checkName.getId()!= kichCo.getId()) return getErro("Tên này đã tồn tại");
        kichCo.setTen(request.getTen());
        kichCo.setTrangThai(request.getTrangThai());
        kichCo.setNgaySua(LocalDateTime.now());
        return ResponseEntity.ok(repository.save(kichCo));
    }

    private String check(String request) {
        if(request==null || request.isBlank()) return "Bạn chưa nhập dữ liệu";
        return "";
    }

    public KichCo udateTrangThai(Long id, Integer status){
        KichCo kichCo = repository.findById(id).get();
        kichCo.setNgaySua(LocalDateTime.now());
        kichCo.setTrangThai(status);
        return repository.save(kichCo);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

}
