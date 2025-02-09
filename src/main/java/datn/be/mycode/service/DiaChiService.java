package datn.be.mycode.service;

import datn.be.mycode.entity.DiaChi;
import datn.be.mycode.entity.KhachHang;
import datn.be.mycode.repository.DiaChiRepository;
import datn.be.mycode.repository.KhachHangRepository;
import datn.be.mycode.request.DiaChi.DiaChiAddRequest;
import datn.be.mycode.request.DiaChi.DiaChiRequest;
import datn.be.mycode.request.customRequest.TableDiaChiKhachHangRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.response.NormalTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiaChiService {

    @Autowired
    private DiaChiRepository repository;

    @Autowired
    private KhachHangRepository khRepo;

    public NormalTableResponse<DiaChi> getAll(TableLongRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<DiaChi> diaChiNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<DiaChi> diaChiPage;
        if (request.getKeyWord() != null) {
            diaChiPage = repository.findAllById(request.getKeyWord(), pageable);

        }else {
            diaChiPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }

        List<DiaChi> diaChis = diaChiPage.getContent().stream().toList();

        diaChiNormalTableResponse.setItem(diaChis);
        diaChiNormalTableResponse.setPage(request.getPage());
        diaChiNormalTableResponse.setPageSize(request.getPageSize());
        diaChiNormalTableResponse.setTotalItem(diaChiPage.getTotalElements());

        return diaChiNormalTableResponse;
    }

    public DiaChi add(DiaChiAddRequest request) {
        var khachHang = khRepo.searchById(request.getIdKhachHang()).get();
        setDiaChiCu(khachHang);

        DiaChi diaChi = new DiaChi();
        diaChi.setIdTinhThanh(request.getIdTinhThanh());
        diaChi.setTinhThanh(request.getTinhThanh());
        diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
        diaChi.setQuanHuyen(request.getQuanHuyen());
        diaChi.setIdPhuongXa(request.getIdPhuongXa());
        diaChi.setPhuongXa(request.getPhuongXa());
        diaChi.setGhiChu(request.getGhiChu());
        diaChi.setTrangThai(1);
        diaChi.setMacDinh(true);
        diaChi.setKhachHang(khachHang);

        return repository.save(diaChi);
    }

    public DiaChi update(DiaChiRequest request) {
        DiaChi diaChi = repository.findById(request.getId()).get();
        var khachHang = khRepo.searchById(request.getIdKhachHang()).get();

        diaChi.setIdTinhThanh(request.getIdTinhThanh());
        diaChi.setTinhThanh(request.getTinhThanh());
        diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
        diaChi.setQuanHuyen(request.getQuanHuyen());
        diaChi.setIdPhuongXa(request.getIdPhuongXa());
        diaChi.setPhuongXa(request.getPhuongXa());
        diaChi.setGhiChu(request.getGhiChu());
        diaChi.setKhachHang(khachHang);
        diaChi.setTrangThai(request.getTrangThai());
        return repository.save(diaChi);
    }

    private void setDiaChiCu(KhachHang khachHang) {
        var diaChiMacDinhCu = repository.findByKhachHangIdAndTrangThai(khachHang.getId(), 1);
        diaChiMacDinhCu.forEach(diaChi -> {
            diaChi.setMacDinh(false);
            repository.save(diaChi);
        });
    }

    @Transactional
    public DiaChi updateTrangThai(Long id, Integer status) {
        // Lấy địa chỉ cần cập nhật
        DiaChi diaChi = repository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ với id: " + id));

        // Nếu trạng thái cần cập nhật là "active" (1)
        if (status == 1) {
            // Đặt trạng thái của tất cả các địa chỉ khác của cùng khách hàng về 0
            repository.updateTrangThaiKhac(diaChi.getKhachHang().getId(), id);
        }

        // Cập nhật trạng thái cho địa chỉ hiện tại
        diaChi.setTrangThai(status);
        return repository.save(diaChi);
    }


    public NormalTableResponse<DiaChi> getDiaChiByIdKhachHang(TableDiaChiKhachHangRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        NormalTableResponse<DiaChi> diaChiNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<DiaChi> diaChiPage;
            diaChiPage = repository.searchByIdKhachHang(request.getIdKhachHang(), pageable);
        List<DiaChi> diaChis = diaChiPage.getContent().stream().toList();

        diaChiNormalTableResponse.setItem(diaChis);
        diaChiNormalTableResponse.setPage(request.getPage());
        diaChiNormalTableResponse.setPageSize(request.getPageSize());
        diaChiNormalTableResponse.setTotalItem(diaChiPage.getTotalElements());

        return diaChiNormalTableResponse;
    }

    public Object setMacDinh(Long idDiaChi) {
        DiaChi diaChi = repository.findById(idDiaChi).get();
        var khachHang = diaChi.getKhachHang();
        setDiaChiCu(khachHang);

        diaChi.setMacDinh(true);
        return repository.save(diaChi);
    }
}
