package datn.be.mycode.service;


import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.entity.SanPham;
import datn.be.mycode.entity.SanPhamChiTiet;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.SanPhamRequest;
import datn.be.mycode.request.customRequest.TableSanPhamRequest;
import datn.be.mycode.request.sanPham.SanPhamByAllReq;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.SanPhamResponse;
import datn.be.mycode.response.sanPham.SanPhamTimKiem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository repo;
    @Autowired
    private GiamGiaRepository giamGiaRepository;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private TheLoaiRepository theLoaiRepository;
    @Autowired
    private LoaiCoRepository loaiCoRepository;
    @Autowired
    private VatLieuRepository vatLieuRepository;
    @Autowired
    private LoaiDeRepository loaiDeRepository;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    public NormalTableResponse<SanPhamResponse> getAll(TableSanPhamRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        if (request.getSort() == null) {
            request.setSort(0);
        }
        NormalTableResponse<SanPhamResponse> sanPhamNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<SanPhamResponse> sanPhamResponsePage;
        if (request.getSort() == 0) {
            sanPhamResponsePage = repo.searchAll(request, pageable);
        }else {
            sanPhamResponsePage = repo.searchAllSortedBySoLuong(request, pageable);
        }
        List<SanPhamResponse> sanPhamResponses = sanPhamResponsePage.getContent();

        sanPhamNormalTableResponse.setItem(sanPhamResponses);
        sanPhamNormalTableResponse.setPage(request.getPage());
        sanPhamNormalTableResponse.setPageSize(request.getPageSize());
        sanPhamNormalTableResponse.setTotalItem(sanPhamResponsePage.getTotalElements());

        return sanPhamNormalTableResponse;
    }

    public List<SanPham> findAllByGiamGiaId(Long idGiamGia){
        return repo.findAllByGiamGiaId(idGiamGia);
    }

    public ResponseEntity<?> timKiem(String tenSanPham) {
        var sanPhams = repo.findAllByName(tenSanPham);
        List<SanPhamTimKiem> sanPhamTimKiems = new ArrayList<>();
        for (SanPham sanPham:sanPhams) {
            SanPhamTimKiem sanPhamTimKiem = new SanPhamTimKiem(sanPham.getId(), sanPham.getTen());
            sanPhamTimKiems.add(sanPhamTimKiem);
        }
        return ResponseEntity.ok(sanPhamTimKiems);
    }

    public SanPham add(SanPhamRequest request) {
        SanPham sanPham = SanPham.builder()
                .ten(request.getTen())
                .moTa(request.getMoTa())
                .hinhAnh(request.getHinhAnh())
                .trongLuong(request.getTrongLuong())
                .beMatSuDung(request.getBeMatSuDung())
                .congNghe(request.getCongNghe())
                .kieuDang(request.getKieuDang())
                .giaThapNhat(BigDecimal.valueOf(0))
                .giaCaoNhat(BigDecimal.valueOf(0))
                .ngayTao(LocalDateTime.now())
                .nguoiTao(request.getNguoiTao())
                .soLuong(0)
                .trangThai(1)
                .giamGia(giamGiaRepository.findById(request.getIdGiamGia()).orElse(null))
                .thuongHieu(thuongHieuRepository.findById(request.getIdThuongHieu()).get())
                .theLoai(theLoaiRepository.findById(request.getIdTheLoai()).get())
                .loaiCo(loaiCoRepository.findById(request.getIdLoaiCo()).get())
                .vatLieu(vatLieuRepository.findById(request.getIdVatLieu()).get())
                .loaiDe(loaiDeRepository.findById(request.getIdLoaiDe()).get())
                .build();
        var sanPhamR = repo.save(sanPham);
        sanPhamR.setMa("SP"+sanPham.getId());

        return repo.save(sanPhamR);
    }


    public SanPham update(SanPhamRequest request) {
        SanPham sanPham = repo.findById(request.getId()).get();
        var idGiamGiaAfter = sanPham.getGiamGia() == null ? null :sanPham.getGiamGia().getId();
        var giamGia = giamGiaRepository.findById(request.getIdGiamGia()).orElse(null);
        if(giamGia!= null && giamGia.getTrangThai()!=1) giamGia = null;

        sanPham.setMa(request.getMa());
        sanPham.setTen(request.getTen());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setHinhAnh(request.getHinhAnh());
        sanPham.setTrongLuong(request.getTrongLuong());
        sanPham.setBeMatSuDung(request.getBeMatSuDung());
        sanPham.setCongNghe(request.getCongNghe());
        sanPham.setKieuDang(request.getKieuDang());
        sanPham.setNgaySua(LocalDateTime.now());
        sanPham.setTrangThai(request.getTrangThai());
        sanPham.setGiamGia(giamGia);
        sanPham.setThuongHieu(thuongHieuRepository.findById(request.getIdThuongHieu()).get());
        sanPham.setTheLoai(theLoaiRepository.findById(request.getIdTheLoai()).get());
        sanPham.setLoaiCo(loaiCoRepository.findById(request.getIdLoaiCo()).get());
        sanPham.setVatLieu(vatLieuRepository.findById(request.getIdVatLieu()).get());
        sanPham.setLoaiDe(loaiDeRepository.findById(request.getIdLoaiDe()).get());

        sanPham = repo.save(sanPham);


//        kiem tra xem giam gia co thay doi
        if(idGiamGiaAfter != request.getIdGiamGia() || idGiamGiaAfter == null && request.getIdGiamGia() != null){
            List<HoaDonChiTiet> hoaDonChiTietsForSanPham = new ArrayList<>();
            var sanPhamChiTietList = sanPhamChiTietRepository.findAllBySanPham_Id(sanPham.getId());
            var mucGiam = sanPham.getGiamGia() == null ? 0 : sanPham.getGiamGia().getMucGiam();
            for (SanPhamChiTiet sanPhamChiTiet:sanPhamChiTietList) {
                var hoaDonChiTiets = hoaDonChiTietRepository.findAllBySanPhamChiTiet_IdAndTrangThai(sanPhamChiTiet.getId(),1);
                for (HoaDonChiTiet hoaDonChiTiet:hoaDonChiTiets) {
                    hoaDonChiTiet.setGiaDaGiamUpdate(mucGiam);
                    hoaDonChiTietsForSanPham.add(hoaDonChiTiet);
                }
            }
            if (hoaDonChiTietsForSanPham != null && !hoaDonChiTietsForSanPham.isEmpty()){
                hoaDonChiTietRepository.saveAll(hoaDonChiTietsForSanPham);
            }
        }
        return sanPham;
    }


    public SanPhamResponse getSanPhamById(Long id){
        return repo.searchById(id);
    }

    public SanPham udateTrangThai(Long id, Integer status){
        SanPham sanPham = repo.findById(id).get();
        var trangThaiBefore = sanPham.getTrangThai();
        sanPham.setTrangThai(status);
        if(sanPham.getTrangThai()!= trangThaiBefore && sanPham.getTrangThai() == 0){
            var sanPhamChiTietList = sanPhamChiTietRepository.findAllBySanPham_Id(sanPham.getId());
            for (SanPhamChiTiet sanPhamChiTiet:sanPhamChiTietList) {
                var hoaDonChiTiets = hoaDonChiTietRepository.findAllBySanPhamChiTiet_IdAndTrangThai(sanPhamChiTiet.getId(),1);

                hoaDonChiTietRepository.deleteAll(hoaDonChiTiets);

            }

        }
        return repo.save(sanPham);
    }

    public void updateSoLuong(SanPham sanPham) {
        sanPham.setSoLuong(sanPhamChiTietRepository.sumSoLuongByTrangThai(sanPham.getId()));
        repo.save(sanPham);
    }

    public void update(SanPham sanPham) {
        repo.save(sanPham);
    }

    public List<SanPham> top5SanPhamBanChay() {
    List<Long> topSanPhamIds = repo.top5SanPhamBanChay();
    return repo.findAllById(topSanPhamIds);
    }

    public List<SanPham> top5SanPhamGiamGia() {
        List<Long> topSanPhamIds = repo.topSanPhamGiamGia();
        return repo.findAllById(topSanPhamIds.stream().limit(5).toList());
    }

    public List<SanPham> top5SanPhamMoi() {
        Pageable pageable = PageRequest.of(0, 5);
        List<SanPham> topSanPhams = repo.top5SanPhamMoi(pageable);
        return topSanPhams;
    }

    public List<SanPham> top3SanPhamGiamGia() {
        List<Long> topSanPhamIds = repo.topSanPhamGiamGia();
        return repo.findAllById(topSanPhamIds.stream().limit(3).toList());
    }
    public SanPham getSanPhamGiamGiaSauNhat() {
        List<SanPham> sanPhams = repo.findSanPhamGiamGiaSauNhat(PageRequest.of(0, 1));
        return sanPhams.isEmpty() ? null : sanPhams.get(0);
    }
    public List<SanPham> getTop12SanPhamByUpdatedDate() {
        List<SanPham> allSanPham = repo.findTop12SanPhamByUpdatedDate();
        return allSanPham.size() > 12 ? allSanPham.subList(0, 12) : allSanPham; // Đảm bảo lấy tối đa 12 sản phẩm
    }

    public ResponseEntity<?> getByAll(SanPhamByAllReq request) {

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<SanPham> sanPhamResponsePage = repo.searchAllSortedByAll(request, pageable);

        List<SanPham> sanPhamResponses = sanPhamResponsePage.getContent();

        NormalTableResponse<SanPham> sanPhamNormalTableResponse = new NormalTableResponse<>();
        sanPhamNormalTableResponse.setItem(sanPhamResponses);
        sanPhamNormalTableResponse.setPage(request.getPage());
        sanPhamNormalTableResponse.setPageSize(request.getPageSize());
        sanPhamNormalTableResponse.setTotalItem(sanPhamResponsePage.getTotalElements());

        return ResponseEntity.ok(sanPhamNormalTableResponse);
    }

    public List<SanPham> getTop5SanPhamByTheLoai(Long theLoaiId) {
        Pageable pageable = PageRequest.of(0, 5); // Trang đầu tiên và giới hạn 5 sản phẩm
        return repo.findTop5SanPhamByTheLoai(theLoaiId, pageable);
    }


    public List<Long> laySanPhamChuaDanhGiaCuaKhachHang(Long idKhachHang) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<HoaDon> recentHoaDons = hoaDonRepository.findRecentHoaDonsByKhachHangId(idKhachHang,sevenDaysAgo);
        Set<Long> productIds = new HashSet<>();

        for (HoaDon hoaDon : recentHoaDons) {
            List<HoaDonChiTiet> chiTietHoaDons = hoaDonChiTietRepository.findByHoaDon(hoaDon);
            for (HoaDonChiTiet chiTiet : chiTietHoaDons) {
                if (!chiTiet.isDanhGia()) {
                    productIds.add(chiTiet.getSanPhamChiTiet().getSanPham().getId());
                }
            }
        }

        return productIds.stream().collect(Collectors.toList());
    }
    public List<SanPhamRequest> layThongTinSanPhamTuIds(List<Long> idSanPham) {
        List<SanPham> sanPhams = repo.findAllById(idSanPham);
        return sanPhams.stream()
                .map(sanPham -> {
                    SanPhamRequest request = new SanPhamRequest();
                    request.setSanPhamRequest(sanPham);
                    return request;
                })
                .collect(Collectors.toList());
    }
}
