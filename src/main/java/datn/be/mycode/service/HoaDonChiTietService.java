package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.HoaDonChiTietRepository;
import datn.be.mycode.repository.HoaDonRepository;
import datn.be.mycode.repository.SanPhamChiTietRepository;
import datn.be.mycode.request.HoaDonChiTietRequest;
import datn.be.mycode.request.NormalTableRequest;
import datn.be.mycode.request.customRequest.BanHang.AddSoLuong;
import datn.be.mycode.response.HoaDonChiTietResponse;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository repo;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @Autowired
    private GiamGiaService giamGiaService;
    @Autowired
    private SanPhamService sanPhamService;


    public NormalTableResponse<HoaDonChiTiet> getAllByIdHoaDon(NormalTableRequest request) {

        CheckRequest(request);


        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<HoaDonChiTiet> hoaDonResponsePage;
        List<HoaDonChiTiet> hoaDonChiTiets;
        var hoaDonNormalTableResponse = new NormalTableResponse<HoaDonChiTiet>();

        hoaDonResponsePage = repo.findAllByHoaDon_Id(Long.valueOf(request.getKeyWord()), pageable);
        hoaDonChiTiets = hoaDonResponsePage.getContent();

        CheckGiamGia(hoaDonChiTiets);

        hoaDonNormalTableResponse.setItem(hoaDonChiTiets);
        hoaDonNormalTableResponse.setPage(request.getPage());
        hoaDonNormalTableResponse.setPageSize(request.getPageSize());
        hoaDonNormalTableResponse.setTotalItem(hoaDonResponsePage.getTotalElements());

        return hoaDonNormalTableResponse;
    }

    public List<HoaDonChiTiet> getListByHoaDonId(Long id){
        return repo.findAllByHoaDon_Id(id);
    }

    public SanPhamChiTiet getSanPhamForHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet){
        var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
        return sanPhamChiTiet;
    }

    public String setSoLuongAfterPayment(Long idHoaDon){
        var hoaDonChiTiets = getListByHoaDonId(idHoaDon);
        for (HoaDonChiTiet hoaDonChiTiet:hoaDonChiTiets) {
            var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            var sanPham = sanPhamChiTiet.getSanPham();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong()-hoaDonChiTiet.getSoLuong());
            if(sanPhamChiTiet.getSoLuong() < 0) return "Sản phảm còn lại không đủ cho hóa đơn này";

            sanPhamChiTietRepository.save(sanPhamChiTiet);
            sanPhamService.updateSoLuong(sanPham);
//            var sanPhamChiTiets = sanPhamChiTietRepository.findAllBySanPham_IdAndTrangThai(sanPham.getId(), 1);
//            if(sanPhamChiTiets.isEmpty() ){
//                sanPham.setTrangThai(0);
//                sanPhamService.update(sanPham);
//            }
        }
        return "";
    }
    public void setSoLuongAfterAdd(Long idHoaDonChiTiet, int soLuongCu) {
        var hoaDonChiTiet = repo.findById(idHoaDonChiTiet).orElse(null);
        if (hoaDonChiTiet == null) return;

        var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
        var sanPham = sanPhamChiTiet.getSanPham();
        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - hoaDonChiTiet.getSoLuong() + soLuongCu);
//        if (sanPhamChiTiet.getSoLuong() == 0) sanPhamChiTiet.setTrangThai(0);

        sanPhamChiTietRepository.save(sanPhamChiTiet);
        sanPhamService.updateSoLuong(sanPham);
//        var sanPhamChiTiets = sanPhamChiTietRepository.findAllBySanPham_IdAndTrangThai(sanPham.getId(), 1);
//        if (sanPhamChiTiets.isEmpty()) {
//            sanPham.setTrangThai(0);
//            sanPhamService.update(sanPham);
//        }

    }

    private void CheckGiamGia(List<HoaDonChiTiet> hoaDonChiTiets) {
        for (HoaDonChiTiet hoaDonChiTiet :hoaDonChiTiets) {
            var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            var sanPham = sanPhamChiTiet.getSanPham();
            var giamGia = sanPham.getGiamGia();

            if(giamGia!=null && giamGia.getNgayKetThuc().isBefore(LocalDateTime.now())){
                giamGia.setTrangThai(3);
                sanPham.setGiamGia(null);

                hoaDonChiTiet.setGiaDaGiam(sanPhamChiTiet.getGiaBan());

                giamGiaService.update(giamGia);
                sanPhamService.update(sanPham);
                repo.save(hoaDonChiTiet);
            }

        }
    }
//    private Integer soLuong(Long id){
//       return repo.soLuongSanPham( id);
//    }

    private void CheckRequest(NormalTableRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        if (request.getStatus() == null) {
            request.setStatus(1);
        }
    }

    public ResponseEntity<?> add(HoaDonChiTietRequest request){

        HoaDonChiTiet hoaDonChiTiet = repo.findByHoaDonIdAndSanPhamChiTietId(request.getIdHoaDon(), request.getIdSanPhamChiTiet());
        var hd = hoaDonRepository.findById(request.getIdHoaDon()).orElse(null);
        var spct = sanPhamChiTietRepository.findById(request.getIdSanPhamChiTiet()).orElse(null);
        var giamGia = spct.getSanPham().getGiamGia();
        var gia = spct.getGiaBan();
        var giaDaGiam = new BigDecimal(gia.toString());
        var soLuongCu = 0;

        if(request.getSoLuong() <= 0) return getErro("Số lượng phải lớn hơn 0");
        if(request.getSoLuong()>spct.getSoLuong()) return getErro("Số lượng sản phẩm không vượt quá số lượng đang có");


        if(giamGia != null && giamGia.getNgayBatDau().isBefore(LocalDateTime.now()) && giamGia.getNgayKetThuc().isAfter(LocalDateTime.now())){
            var mucGiam = BigDecimal.valueOf(giamGia.getMucGiam()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            giaDaGiam = gia.subtract(gia.multiply(mucGiam));
        }
        var tongTienSanPhamChuaGiam = hd.getTongTienSanPhamChuaGiam();

        if(hoaDonChiTiet==null) {
            if(hd!=null && spct!=null) {
                hoaDonChiTiet = new HoaDonChiTiet().builder()
                        .soLuong(request.getSoLuong())
                        .giaHienHanh(gia)
                        .giaDaGiam(giaDaGiam)
                        .trangThai(1)
                        .danhGia(false)
                        .hoaDon(hd)
                        .sanPhamChiTiet(spct)
                        .danhGia(false)
                        .build();
            }
            else{
                return getErro("Hóa đơn hoặc sản phẩm này chưa tồn tại");
            }
        }

        else{
            soLuongCu = hoaDonChiTiet.getSoLuong();
            tongTienSanPhamChuaGiam = tongTienSanPhamChuaGiam.subtract(hoaDonChiTiet.getGiaDaGiam().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
            hoaDonChiTiet.AddSoluong(request.getSoLuong());
            if(hoaDonChiTiet.getSoLuong()>spct.getSoLuong()) return getErro("Số lượng sản phẩm trong hóa đơn không vượt quá số lượng sản phẩm đang có");
        }

        var hoaDonChiTietRes = new HoaDonChiTietResponse(repo.save(hoaDonChiTiet));


        tongTienSanPhamChuaGiam = tongTienSanPhamChuaGiam.add(hoaDonChiTietRes.getTong());

        hd.setTongTienSanPhamChuaGiam(tongTienSanPhamChuaGiam);
        hoaDonRepository.save(hd);
        setSoLuongAfterAdd(hoaDonChiTiet.getId(), soLuongCu);

        return ResponseEntity.ok(hoaDonChiTietRes);
    }


    public ResponseEntity<?> delete(Long id){
        try{
            var hoaDonChiTiet = repo.findById(id).get();
            var hoaDon = hoaDonChiTiet.getHoaDon();
            var tienSauKhiXoaSanPham = hoaDon.getTongTienSanPhamChuaGiam().subtract(hoaDonChiTiet.getGiaDaGiam().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
            int soLuongCu = hoaDonChiTiet.getSoLuong()*2;
            hoaDon.setTongTienSanPhamChuaGiam(tienSauKhiXoaSanPham);
            setSoLuongAfterAdd(hoaDonChiTiet.getId(), soLuongCu);
            repo.delete(hoaDonChiTiet);
            hoaDonRepository.save(hoaDon);
            return ResponseEntity.ok("Xóa thành công");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(400,"Xóa thất bại"));
        }
    }

    public ResponseEntity<?> addSoLuong(AddSoLuong request) {
        var hoaDonChiTiet = repo.findById(request.getIdHoaDonChiTiet()).orElse(null);

        if (hoaDonChiTiet == null) return getErro("Không tìm thấy hóa đơn chi tiết");
        var spct = hoaDonChiTiet.getSanPhamChiTiet();
        var soLuongTong = spct.getSoLuong() + hoaDonChiTiet.getSoLuong();
        if (request.getSoLuong() <= 0) return getErro("Số lượng phải lớn hơn 0");
        int soLuongSauThem = request.getSoLuong();
        int soLuongCu = hoaDonChiTiet.getSoLuong();
        if (soLuongSauThem > soLuongTong) return getErro("Số lượng vượt quá số lượng đang có");
        hoaDonChiTiet.setSoLuong(soLuongSauThem);
        setSoLuongAfterAdd(hoaDonChiTiet.getId(), soLuongCu);
        return ResponseEntity.ok(repo.save(hoaDonChiTiet));
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

    public String checkSoLuong(Long idHoaDon) {
        List<HoaDonChiTiet> hoaDonChiTietForHoaDon = repo.findAllByHoaDon_Id(idHoaDon);

        for (HoaDonChiTiet hoaDonChiTiet:hoaDonChiTietForHoaDon) {
            var soLuongHoaDonChiTiet = hoaDonChiTiet.getSoLuong();
            var soLuongSanPham = hoaDonChiTiet.getSanPhamChiTiet().getSoLuong();
            if(soLuongSanPham == 0) return "Sản phảm " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getMa() +" đã hết";
            if(soLuongHoaDonChiTiet > soLuongSanPham) {
                return "Sản phảm " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getMa() +" không đủ số lượng";
            }
        }
        return null;
    }

    public void setTrangThaiByHoaDonDone(HoaDon hoaDon) {
        var hoaDonCHiTiets = repo.findAllByHoaDon_Id(hoaDon.getId());

        for (HoaDonChiTiet hoaDonChiTiet:hoaDonCHiTiets) {
            hoaDonChiTiet.setTrangThai(0);
        }
        repo.saveAll(hoaDonCHiTiets);
    }

    public List<SanPham> timSanPhamChuaCmtTrong7NgayThanhToan() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        return repo.timSanPhamChuaCmtTrong7NgayThanhToan(startDate, endDate);
    }
    public List<SanPham> getNhungSanPhamTrongHoaDonChiTietIsCMTFalseByIdKhTrong7Ngay(Long idKhachHang) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(7); // 7 ngày trước
        LocalDateTime endDate = now;               // hiện tại

        return repo.getNhungSanPhamTrongHoaDonChiTietIsCMTFalseByIdKh(idKhachHang, startDate, endDate);
    }

    public List<HoaDonChiTiet> getHoaDonChiTietByKhachHangIdAndWithin7Days(Long idKhachHang) {
        // Tính ngày 7 ngày trước từ hiện tại
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        // Lấy danh sách hóa đơn chi tiết trong vòng 7 ngày
        return repo.findAllByKhachHangIdAndWithin7Days(idKhachHang, sevenDaysAgo);
    }
}
