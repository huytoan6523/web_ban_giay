package datn.be.mycode.service;

import datn.be.mycode.entity.PhuongThucThanhToan;
import datn.be.mycode.repository.ChucVuRepository;
import datn.be.mycode.repository.PhuongThucThanhToanRepository;
import datn.be.mycode.request.ChucVuRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.PhuongThucThanhToanRequest;
import datn.be.mycode.response.NormalTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhuongThucThanhToanService {

    @Autowired
    private PhuongThucThanhToanRepository repo;

    public NormalTableResponse<PhuongThucThanhToan> getAll(NormalTableRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        NormalTableResponse<PhuongThucThanhToan> theLoaiNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<PhuongThucThanhToan> phuongThucThanhToanPage;

        phuongThucThanhToanPage = repo.searchByTen(request.getKeyWord(), request.getStatus(), pageable);

        List<PhuongThucThanhToan> phuongThucThanhToans = phuongThucThanhToanPage.getContent();

        theLoaiNormalTableResponse.setItem(phuongThucThanhToans);
        theLoaiNormalTableResponse.setPage(request.getPage());
        theLoaiNormalTableResponse.setPageSize(request.getPageSize());
        theLoaiNormalTableResponse.setTotalItem(phuongThucThanhToanPage.getTotalElements());

        return theLoaiNormalTableResponse;
    }


    public PhuongThucThanhToan add(String request) {
        PhuongThucThanhToan phuongThucThanhToan = new PhuongThucThanhToan();
        phuongThucThanhToan.setTen(request);
        phuongThucThanhToan.setTrangThai(1);
        phuongThucThanhToan.setNgayTao(LocalDateTime.now());
        return repo.save(phuongThucThanhToan);
    }


    public PhuongThucThanhToan update(PhuongThucThanhToanRequest request) {
        PhuongThucThanhToan phuongThucThanhToan = repo.findById(request.getId()).get();
        phuongThucThanhToan.setTen(request.getTen());
        phuongThucThanhToan.setTrangThai(request.getTrangThai());
        phuongThucThanhToan.setNgaySua(LocalDateTime.now());
        return repo.save(phuongThucThanhToan);
    }


    public PhuongThucThanhToan udateTrangThai(Long id, Integer status){
        PhuongThucThanhToan phuongThucThanhToan = repo.findById(id).get();
        phuongThucThanhToan.setTrangThai(status);
        return repo.save(phuongThucThanhToan);
    }
}
