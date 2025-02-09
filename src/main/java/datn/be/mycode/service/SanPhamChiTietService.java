package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.SanPhamChiTietRequest;
import datn.be.mycode.request.customRequest.TableSanPhamChiTietRequest;
import datn.be.mycode.request.sanPhamChiTiet.GetBySanPhamAndMauReq;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.SanPhamChiTietResponse;
import datn.be.mycode.response.customResponse.SanPhamChiTiet.SanPhamBanHang;
import datn.be.mycode.response.customResponse.SanPhamChiTiet.SanPhamChiTietByIdSP;
import datn.be.mycode.response.customResponse.SanPhamChiTiet.SanPhamChiTietByIdSanPhamRes;
import datn.be.mycode.response.erroResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SanPhamChiTietService {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private MauSacRepository mauSacRepository;
    @Autowired
    private KichcoRepository kichcoRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;


    public NormalTableResponse<SanPhamChiTietResponse> getAll(TableSanPhamChiTietRequest request) {
        CheckRequest(request);

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<SanPhamChiTietResponse> sanPhamChiTietResponsePage;
        if (request.getSort() == 0) {
            System.out.println(request.toString());
            sanPhamChiTietResponsePage = sanPhamChiTietRepository.searchAll( request, pageable);
        }else {
            sanPhamChiTietResponsePage = sanPhamChiTietRepository.searchAllSortedBySoLuong( request, pageable);
        }
        List<SanPhamChiTietResponse> sanPhamChiTietResponses = sanPhamChiTietResponsePage.getContent().stream().toList();

        var sanPhamNormalTableResponse = new NormalTableResponse<>(sanPhamChiTietResponses,request.getPage(),request.getPageSize(),sanPhamChiTietResponsePage.getTotalElements());

        return sanPhamNormalTableResponse;
    }

    public SanPhamChiTietByIdSanPhamRes getByIdSanPham(Long idSanPham, int trangThai){

        List<SanPhamChiTiet> listSanPhamChiTiet = sanPhamChiTietRepository.findAllBySanPham_IdAndTrangThai(idSanPham,1);
        if(trangThai == 0){
            listSanPhamChiTiet = sanPhamChiTietRepository.findAllBySanPham_Id(idSanPham);
        }
        if(listSanPhamChiTiet.isEmpty()) return null;

        List<SanPhamChiTietByIdSP> listSanPhamChiTietRes = getSanPhamChiTietsBySanPham(listSanPhamChiTiet);

        SanPham sanPhamRes = listSanPhamChiTiet.get(0).getSanPham();

        SanPhamChiTietByIdSanPhamRes sanPhamChiTietByIdSanPhamRes = new SanPhamChiTietByIdSanPhamRes(sanPhamRes, listSanPhamChiTietRes);
        return sanPhamChiTietByIdSanPhamRes;
    }

    public boolean  checkTrungKichCoVaMauSac(Long sanPhamId, Long kichCoId, Long mauSacId, Long sanPhamChiTietId){
        return sanPhamChiTietRepository.existsBySanPhamAndKichCoAndMauSac(sanPhamId, kichCoId, mauSacId, sanPhamChiTietId);
    }
    private ResponseEntity<?> getErro(String string){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(string));
    }
    public Boolean checkTrungKichCoVaMauSacAddandupdate(SanPhamChiTietRequest request ){
        var sanPhamId = request.getSanPham() ;
        var kichCoId = request.getKichCo() ;
        var mauSacId = request.getMauSac() ;
        Long sanPhamChiTietId = request.getId() ;

        if (checkTrungKichCoVaMauSac(sanPhamId, kichCoId, mauSacId, sanPhamChiTietId)){
            return false;
        }else {
            return true;
        }
    }

    public ResponseEntity<?> addSPCT(SanPhamChiTietRequest request){
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        SanPham sanPham = sanPhamRepository.findById(request.getSanPham()).get();

        sanPhamChiTiet.setNgayTao(LocalDateTime.now());
        sanPhamChiTiet.setHinhAnh(request.getHinhAnh());
        sanPhamChiTiet.setGiaNhap(request.getGiaNhap());
        sanPhamChiTiet.setGiaBan(request.getGiaBan());
        sanPhamChiTiet.setSoLuong(request.getSoLuong());
        sanPhamChiTiet.setTrangThai(request.getTrangThai());
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setMauSac(mauSacRepository.findById(request.getMauSac()).get());
        sanPhamChiTiet.setKichCo(kichcoRepository.findById(request.getKichCo()).get());

        if (checkTrungKichCoVaMauSacAddandupdate(request)==false) {
            return getErro("Có Kích cỡ và Màu sắc trùng lặp trong sản phẩm này");
        } else{
            SanPhamChiTiet sanPhamChiTietR = sanPhamChiTietRepository.save(sanPhamChiTiet);

            CheckPriceMinAndMax(sanPham);

            return ResponseEntity.ok(sanPhamChiTietR);
        }

    }

    private void CheckPriceMinAndMax(SanPham sanPham) {
        var soLuong = sanPhamChiTietRepository.sumSoLuongByTrangThai(sanPham.getId());
        var giaThapNhat = sanPhamChiTietRepository.findGiaMinByIdSanPham(sanPham.getId());
        var giaCaoNhat = sanPhamChiTietRepository.findGiaMaxByIdSanPham(sanPham.getId());

        sanPham.setGiaThapNhat(giaThapNhat);
        sanPham.setGiaCaoNhat(giaCaoNhat);
        sanPham.setSoLuong(soLuong);

        sanPhamRepository.save(sanPham);
    }

    public ResponseEntity<?> update(SanPhamChiTietRequest request){
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(request.getId()).get();
        var giaBan = new BigDecimal(sanPhamChiTiet.getGiaBan().toString());
        SanPham sanPham = sanPhamRepository.findById(request.getSanPham()).get();

        sanPhamChiTiet.setNgaySua(LocalDateTime.now());
        sanPhamChiTiet.setHinhAnh(request.getHinhAnh());
        sanPhamChiTiet.setGiaNhap(request.getGiaNhap());
        sanPhamChiTiet.setGiaBan(request.getGiaBan());
        sanPhamChiTiet.setSoLuong(request.getSoLuong());
        sanPhamChiTiet.setTrangThai(request.getTrangThai());
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setMauSac(mauSacRepository.findById(request.getMauSac()).get());
        sanPhamChiTiet.setKichCo(kichcoRepository.findById(request.getKichCo()).get());

        if (checkTrungKichCoVaMauSacAddandupdate(request)==false) {
            return getErro("Có Kích cỡ và Màu sắc trùng lặp trong sản phẩm này");
        }

        sanPhamChiTiet = sanPhamChiTietRepository.save(sanPhamChiTiet);

//        kiem tra xem co thay doi gia ban
        if(sanPhamChiTiet.getGiaBan().compareTo(giaBan) != 0){
            var check = sanPhamChiTiet.getGiaBan().equals(giaBan);
            var hoaDonChiTietsForSanPhamChiTiet = hoaDonChiTietRepository.findAllBySanPhamChiTiet_IdAndTrangThai(sanPhamChiTiet.getId(), 1);
            var giamGia = sanPham.getGiamGia() == null ? 0 :sanPham.getGiamGia().getMucGiam();
            for (HoaDonChiTiet hoaDonChiTiet:hoaDonChiTietsForSanPhamChiTiet) {
                if (hoaDonChiTiet.getTrangThai() == 1) {
                    hoaDonChiTiet.setGiaHienHanh(sanPhamChiTiet.getGiaBan());
                    hoaDonChiTiet.setGiaDaGiamUpdate(giamGia);
                    hoaDonChiTietRepository.save(hoaDonChiTiet);
                }
            }

        }

//        gia nho nhat va lon nhat cua san pham
        CheckPriceMinAndMax(sanPham);

        return ResponseEntity.ok(sanPhamChiTiet);
    }

    public SanPhamChiTiet udateTrangThai(Long id, Integer status){
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).get();
        int trangThai = sanPhamChiTiet.getTrangThai();
        int soLuong = sanPhamChiTiet.getSoLuong();
        SanPham sanPham = sanPhamRepository.findById(sanPhamChiTiet.getSanPham().getId()).get();

        sanPhamChiTiet.setTrangThai(status);

        sanPhamChiTiet = sanPhamChiTietRepository.save(sanPhamChiTiet);

        if(sanPhamChiTiet.getTrangThai()!= trangThai){
            if(sanPhamChiTiet.getTrangThai() == 0){
            sanPham.setSoLuong(sanPham.getSoLuong() - sanPhamChiTiet.getSoLuong());
            } else{
            sanPham.setSoLuong(sanPham.getSoLuong() + sanPhamChiTiet.getSoLuong());
            }
        }
        sanPhamRepository.save(sanPham);

        return sanPhamChiTiet;
    }

    public NormalTableResponse<SanPhamBanHang> getAllSanPham(TableSanPhamChiTietRequest request) {

        CheckRequest(request);

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<SanPhamChiTietResponse> sanPhamChiTietResponsePage;

        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(pageable);
        List<SanPham> sanPhams = sanPhamPage.getContent();
        List<SanPhamBanHang> sanPhamBanHangs = new ArrayList<>();
        if (sanPhams!=null) {
            for (SanPham sanPham : sanPhams) {
                SanPhamBanHang sanPhamBanHang = new SanPhamBanHang();
                sanPhamBanHang.setSanPham(sanPham);
                sanPhamBanHang.setSanPhamChiTiets(sanPhamChiTietRepository.findAllBySanPham_Id(sanPham.getId()));
                sanPhamBanHangs.add(sanPhamBanHang);
            }
        }

        var sanPhamNormalTableResponse = new NormalTableResponse<>(sanPhamBanHangs,request.getPage(),request.getPageSize(),sanPhamPage.getTotalElements());


        return sanPhamNormalTableResponse;

    }

    private void CheckRequest(TableSanPhamChiTietRequest request) {
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
    }

    List<SanPhamChiTietByIdSP> getSanPhamChiTietsBySanPham(List<SanPhamChiTiet> listSanPhamChiTiet){

        List<SanPhamChiTietByIdSP> listSanPhamChiTietRes = new ArrayList<>();

        listSanPhamChiTiet.forEach(sanPhamChiTiet -> {
            SanPhamChiTietByIdSP sanPhamChiTietByIdSP = SanPhamChiTietByIdSP.builder()
                    .id(sanPhamChiTiet.getId())
                    .ngayTao(sanPhamChiTiet.getNgayTao())
                    .ngaySua(sanPhamChiTiet.getNgaySua())
                    .hinhAnh(sanPhamChiTiet.getHinhAnh())
                    .giaNhap(sanPhamChiTiet.getGiaNhap())
                    .giaBan(sanPhamChiTiet.getGiaBan())
                    .soLuong(sanPhamChiTiet.getSoLuong())
                    .trangThai(sanPhamChiTiet.getTrangThai())
                    .sanPham(sanPhamChiTiet.getSanPham().getId())
                    .mauSac(sanPhamChiTiet.getMauSac())
                    .kichCo(sanPhamChiTiet.getKichCo())
                    .build();
            listSanPhamChiTietRes.add(sanPhamChiTietByIdSP);
        });

        return listSanPhamChiTietRes;
    }

    public ResponseEntity<?> getAllMauBySanPham(Long idSanPham) {
        var sanPhamChiTiets = sanPhamChiTietRepository.findAllBySanPham_IdAndTrangThai(idSanPham,1);
        List<MauSac> mauSacs = new ArrayList<>();
        for (SanPhamChiTiet sanPhamChiTiet:sanPhamChiTiets) {
            var mauSac = sanPhamChiTiet.getMauSac();
            mauSacs.add(mauSac);
        }
        return ResponseEntity.ok(mauSacs);
    }

    public ResponseEntity<?> getAllSizeBySanPhamAndMau(GetBySanPhamAndMauReq request) {
        var sanPhamChiTiets = sanPhamChiTietRepository.findAllBySanPham_IdAndMauSacId(request.getIdSanPham(),request.getIdMau());
        return ResponseEntity.ok(sanPhamChiTiets);
    }

    public Map<String, List<Long>> getDistinctMauSacAndKichCoBySanPhamId(Long idSanPham) {
        // Lấy danh sách idMauSac và idKichCo không trùng lặp
        List<Long> mauSacList = sanPhamChiTietRepository.findDistinctMauSacBySanPhamId(idSanPham);
        List<Long> kichCoList = sanPhamChiTietRepository.findDistinctKichCoBySanPhamId(idSanPham);

        // Trả về dưới dạng Map hoặc có thể trả về List nếu cần
        return Map.of("idMauSac", mauSacList, "idKichCo", kichCoList);
    }

}
