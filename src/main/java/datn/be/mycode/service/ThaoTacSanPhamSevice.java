package datn.be.mycode.service;

import datn.be.mycode.entity.SanPham;
import datn.be.mycode.entity.ThaoTacSanPham;
import datn.be.mycode.repository.SanPhamRepository;
import datn.be.mycode.repository.ThaoTacSanPhamRepository;
import datn.be.mycode.request.SanPhamRequest;
import datn.be.mycode.request.ThaoTacSanPhamRequest;
import datn.be.mycode.request.customRequest.TableSanPhamRequest;
import datn.be.mycode.request.customRequest.TableThaoTacSanPham;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.SanPhamResponse;
import datn.be.mycode.response.ThaoTacSanPhamResPonse;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThaoTacSanPhamSevice {
    @Autowired
    private ThaoTacSanPhamRepository repo;

    public NormalTableResponse<ThaoTacSanPhamResPonse> getAll(TableThaoTacSanPham request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        NormalTableResponse<ThaoTacSanPhamResPonse> thaotacsanPhamNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<ThaoTacSanPhamResPonse> thaotacsanPhamNormalTableResponsePage
        = repo.searchAll(request.getKeyWord(), request.getStartDate(), request.getStatus(), pageable);

      List<ThaoTacSanPhamResPonse> thaotacsanPhamResponses = thaotacsanPhamNormalTableResponsePage.getContent().stream().toList();

        thaotacsanPhamNormalTableResponse.setItem(thaotacsanPhamResponses);
        thaotacsanPhamNormalTableResponse.setPage(request.getPage());
        thaotacsanPhamNormalTableResponse.setPageSize(request.getPageSize());
        thaotacsanPhamNormalTableResponse.setTotalItem(thaotacsanPhamNormalTableResponsePage.getTotalElements());

        return thaotacsanPhamNormalTableResponse;
    }


    public ThaoTacSanPham add(ThaoTacSanPhamRequest request) {
        ThaoTacSanPham thaotacsanPham = new ThaoTacSanPham ();
//
        thaotacsanPham.setThaoTac(request.getThaoTac());
        thaotacsanPham.setNgayTao(LocalDateTime.now());
        thaotacsanPham.setTrangThai(1);
        return repo.save(thaotacsanPham);
    }


    public ThaoTacSanPham update(ThaoTacSanPhamRequest request) {
        ThaoTacSanPham thaotacsanPham = repo.findById(request.getId()).get();
        thaotacsanPham.setThaoTac(request.getThaoTac());
        thaotacsanPham.setNgayTao(LocalDateTime.now());
        thaotacsanPham.setTrangThai(request.getTrangThai());
        return repo.save(thaotacsanPham);
    }


//    public void delete(SanPham sanPham) {
//        repo.delete(sanPham);
//    }


    public ThaoTacSanPham  udateTrangThai(Long id, Integer status){
        ThaoTacSanPham  thaotacsanPham = repo.findById(id).get();
        thaotacsanPham.setTrangThai(status);
        return repo.save(thaotacsanPham);
    }
}
