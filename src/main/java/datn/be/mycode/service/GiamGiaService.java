package datn.be.mycode.service;

import datn.be.mycode.entity.GiamGia;
import datn.be.mycode.entity.KichCo;
import datn.be.mycode.entity.SanPham;
import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.repository.GiamGiaRepository;
import datn.be.mycode.repository.KichcoRepository;
import datn.be.mycode.repository.SanPhamRepository;
import datn.be.mycode.request.*;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import jakarta.annotation.PostConstruct;
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
public class GiamGiaService {
    @Autowired
    private GiamGiaRepository repository;
    @Autowired
    private SanPhamService sanPhamService;

//    @PostConstruct
    public void loadGiamGia(){
        var giamGias = repository.findAllByTrangThai(1);
        var now = LocalDateTime.now();
        for (GiamGia giamGia:giamGias) {
            var endDay = giamGia.getNgayKetThuc();
            var startDay = giamGia.getNgayBatDau();
            var trangThai = giamGia.getTrangThai();
            if (endDay.isBefore(now)) {
                // Voucher đã quá hạn
                if(trangThai != 3) {
                    giamGia.setTrangThai(3);
                    repository.save(giamGia);
                    loadSanPham(giamGia);
                }
            }
            if (startDay.isAfter(now)) {
                // Voucher chưa đến sự kiện
                if(trangThai != 2) {
                    giamGia.setTrangThai(2);
                    repository.save(giamGia);
                }
            }
            if (startDay.isBefore(now) || startDay.isEqual(now)) {
                // Voucher đang trong thời gian sự kiện (startDay <= now <= endDay)
                if(trangThai != 1) {
                    giamGia.setTrangThai(1);
                    repository.save(giamGia);
                }
            }
            if(trangThai == 3) {
                loadSanPham(giamGia);
            }
        }
    }

    public NormalTableResponse<GiamGia> getAll(NormalTableRequest request){
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        NormalTableResponse<GiamGia> GiamGiaNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable= PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<GiamGia> Page;
        if (request.getKeyWord() != null) {
            Page = repository.findByTen(request.getKeyWord(), pageable);
        }else {
           Page = repository.findAllByTrangThai(request.getStatus(), pageable);
        }
        List<GiamGia> giamGias = Page.getContent().stream().toList();

        GiamGiaNormalTableResponse.setItem(giamGias);
        GiamGiaNormalTableResponse.setPage(request.getPage());
        GiamGiaNormalTableResponse.setPageSize(request.getPageSize());
        GiamGiaNormalTableResponse.setTotalItem(Page.getTotalElements());
        return GiamGiaNormalTableResponse;

    }

    public ResponseEntity<?> getAll (){

        return ResponseEntity.ok(repository.findAllByNgayBatDauBeforeAndNgayKetThucAfter(LocalDateTime.now(),LocalDateTime.now()));
    }

    public GiamGia getById(Long id){

        return repository.findById(id).get();
    }

    public ResponseEntity<?> add(GiamGiaRequest request){
        if (request.getTen()== null || request.getTen().isBlank()) return getErro("không bỏ trống tên");
        if (request.getMucGiam() <=0 ) return getErro("Mức giảm phải lớn hơn 0");
        if (request.getMucGiam() > 100 ) return getErro("Mức giảm phải nhỏ hơn 100%");
        if (request.getNgayBatDau() == null) return getErro("không bỏ trống ngày bắt đầu");
        if (request.getNgayKetThuc() == null) return getErro("không bỏ trống ngày kết thúc");
        if (request.getNgayBatDau().isAfter(request.getNgayKetThuc())) return getErro("Ngày kết thúc phải sau ngày bắt đầu");
        var checkName = repository.findByTen(request.getTen()).orElse(null);
        if (checkName != null) return getErro("Tên này đã tồn tại");

        GiamGia giamGia= new GiamGia();
        giamGia.setTen(request.getTen());
        giamGia.setNgayBatDau(request.getNgayBatDau());
        giamGia.setNgayKetThuc(request.getNgayKetThuc());
        giamGia.setMucGiam(request.getMucGiam());
        giamGia.setTrangThai(1);
        giamGia.setNgayTao(LocalDateTime.now());

        return ResponseEntity.ok(repository.save(giamGia));
    }

    public ResponseEntity<?> update(GiamGiaRequest request) {
        if (request.getNgayBatDau().isAfter(request.getNgayKetThuc())) return getErro("Ngày kết thúc phải sau ngày bắt đầu");
        var checkName = repository.findByTen(request.getTen()).orElse(null);

        GiamGia giamGia = repository.findById(request.getId()).get();
        if (checkName != null && checkName.getId()!=giamGia.getId()) return getErro("Tên này đã tồn tại");
        var trangThaiBefore = giamGia.getTrangThai();
        giamGia.setTen(request.getTen());
        giamGia.setNgayBatDau(request.getNgayBatDau());
        giamGia.setNgayKetThuc(request.getNgayKetThuc());
        giamGia.setMucGiam(request.getMucGiam());
        giamGia.setNgaySua(LocalDateTime.now());
        checkGiamGia(giamGia);
        if(trangThaiBefore == 1 && trangThaiBefore != giamGia.getTrangThai()){
            loadSanPham(giamGia);
        }
        return ResponseEntity.ok(repository.save(giamGia));
    }

    private void checkGiamGia(GiamGia giamGia) {
        var endDay = giamGia.getNgayKetThuc();
        var startDay = giamGia.getNgayBatDau();
        var now = LocalDateTime.now();
        if (endDay.isBefore(now)) {
            // Voucher đã quá hạn
            giamGia.setTrangThai(3);
        } else if (startDay.isAfter(now)) {
            // Voucher chưa đến sự kiện
            giamGia.setTrangThai(2);
        } else if (startDay.isBefore(now) || startDay.isEqual(now)) {
            // Voucher đang trong thời gian sự kiện (startDay <= now <= endDay)
            giamGia.setTrangThai(1);
        }
    }

    private void loadSanPham(GiamGia giamGia){
        var sanPhams = sanPhamService.findAllByGiamGiaId(giamGia.getId());
        if(sanPhams.isEmpty()) return;
        for (SanPham sanPham:sanPhams) {
            sanPham.setGiamGia(null);
            SanPhamRequest sanPhamRequest = new SanPhamRequest();
            sanPhamRequest.setSanPhamRequest(sanPham);
            sanPhamService.update(sanPhamRequest);
        }
    }


    public void delete(GiamGia giamGia) {
        repository.delete(giamGia);
    }


    public GiamGia udateTrangThai(Long id, Integer status){
       GiamGia giamGia = repository.findById(id).get();
       giamGia.setTrangThai(status);
        return repository.save(giamGia);
    }

    public void update(GiamGia giamGia) {
        repository.save(giamGia);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }
}
