package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.DanhGiaSanPhamRequest;
import datn.be.mycode.request.customRequest.TableLongRequest;
import datn.be.mycode.request.danhGiaSanPham.DanhGiaByKhachHangIdRequest;
import datn.be.mycode.request.danhGiaSanPham.DanhGiaBySanPhamIdRequest;
import datn.be.mycode.request.khachHang.DanhGiaSanPhamChungRequest;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.sanPham.DanhGiaSanPhamResponse;
import datn.be.mycode.response.sanPham.SanPhamDanhGiaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DanhGiaSanPhamService {
    @Autowired
    DanhGiaSanPhamRepository danhGiaSanPhamRepository;
     @Autowired
     SanPhamRepository sanPhamRepository;
     @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepository;
     @Autowired
     KhachHangRepository khachHangRepository;
     @Autowired
     HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    HinhAnhBinhLuanRepository hinhAnhBinhLuanRepository;
    public NormalTableResponse<DanhGiaSanPham> getAll(TableLongRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        NormalTableResponse<DanhGiaSanPham> danhGiaSanPhamNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<DanhGiaSanPham> danhGiaSanPhamPage;

        if (request.getKeyWord() != null) {
            danhGiaSanPhamPage = danhGiaSanPhamRepository.findAllById(request.getKeyWord(), pageable);
        }else if (request.getStatus() != null){
            danhGiaSanPhamPage = danhGiaSanPhamRepository.findAllByTrangThai(request.getStatus(), pageable);
        }else {
            danhGiaSanPhamPage = danhGiaSanPhamRepository.findAll( pageable);
        }

        List<DanhGiaSanPham> lichSuSuDungs = danhGiaSanPhamPage.getContent().stream().toList();

        danhGiaSanPhamNormalTableResponse.setItem(lichSuSuDungs);
        danhGiaSanPhamNormalTableResponse.setPage(request.getPage());
        danhGiaSanPhamNormalTableResponse.setPageSize(request.getPageSize());
        danhGiaSanPhamNormalTableResponse.setTotalItem(danhGiaSanPhamPage.getTotalElements());

        return danhGiaSanPhamNormalTableResponse;
    }


    public DanhGiaSanPham udateTrangThai(Long id, Integer trangThai){
        DanhGiaSanPham danhGiaSanPham = danhGiaSanPhamRepository.findById(id).get();
        danhGiaSanPham.setTrangThai(trangThai);
        return danhGiaSanPhamRepository.save(danhGiaSanPham);
    }


    private void updateTrungBinhDanhGia(Long idSanPham) {
        List<DanhGiaSanPham> danhGiaList = danhGiaSanPhamRepository.findByIdSanPham(idSanPham);

        if (danhGiaList.isEmpty()) {
            System.out.println("Không có đánh giá nào cho sản phẩm với ID: " + idSanPham);
            return; // Không cập nhật gì nếu không có đánh giá
        }

        double trungBinhSao = danhGiaList.stream()
                .mapToInt(DanhGiaSanPham::getSao)
                .average()
                .orElse(0);

        Optional<SanPham> sanPhamOptional = sanPhamRepository.findById(idSanPham);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            sanPham.setTrungBinhDanhGia((int) Math.round(trungBinhSao));

            try {
                sanPhamRepository.save(sanPham);
            } catch (Exception e) {
                // Log lỗi nếu có
                System.out.println("Lỗi khi lưu sản phẩm: " + e.getMessage());
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + idSanPham);
        }
    }

    public NormalTableResponse<DanhGiaSanPham> getDanhGiaByKhachHangId(DanhGiaByKhachHangIdRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<DanhGiaSanPham> danhGiaSanPhamNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<DanhGiaSanPham> danhGiaSanPhamPage;

        danhGiaSanPhamPage = danhGiaSanPhamRepository.findDanhGiaByKhachHangId(request.getIdKhachHang(), pageable);

        List<DanhGiaSanPham> lichSuSuDungs = danhGiaSanPhamPage.getContent().stream().toList();

        danhGiaSanPhamNormalTableResponse.setItem(lichSuSuDungs);
        danhGiaSanPhamNormalTableResponse.setPage(request.getPage());
        danhGiaSanPhamNormalTableResponse.setPageSize(request.getPageSize());
        danhGiaSanPhamNormalTableResponse.setTotalItem(danhGiaSanPhamPage.getTotalElements());

        return danhGiaSanPhamNormalTableResponse;
    }


    public NormalTableResponse<DanhGiaSanPham> getDanhGiaBySanPhamId(DanhGiaBySanPhamIdRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        NormalTableResponse<DanhGiaSanPham> danhGiaSanPhamNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<DanhGiaSanPham> danhGiaSanPhamPage;

        danhGiaSanPhamPage = danhGiaSanPhamRepository.findDanhGiaBySanPhamId(request.getIdSanPham(), pageable);

        List<DanhGiaSanPham> lichSuSuDungs = danhGiaSanPhamPage.getContent().stream().toList();

        danhGiaSanPhamNormalTableResponse.setItem(lichSuSuDungs);
        danhGiaSanPhamNormalTableResponse.setPage(request.getPage());
        danhGiaSanPhamNormalTableResponse.setPageSize(request.getPageSize());
        danhGiaSanPhamNormalTableResponse.setTotalItem(danhGiaSanPhamPage.getTotalElements());

        return danhGiaSanPhamNormalTableResponse;
    }

    // danh gia tung san pham sp1
    @Transactional
    public DanhGiaSanPham danhGiaTungSanPham(DanhGiaSanPhamChungRequest request) {
        // Tìm chi tiết hóa đơn dựa trên idSanPham
        List<HoaDonChiTiet> chiTietHoaDons = hoaDonChiTietRepository.findBySanPhamChiTiet_SanPham_Id(request.getIdSanPham());

        // Kiểm tra nếu không có chi tiết hóa đơn nào
        if (chiTietHoaDons.isEmpty()) {
            return null; // Hoặc ném ngoại lệ tùy theo cách bạn muốn xử lý
        }

        // Tạo một đối tượng đánh giá mới
        DanhGiaSanPham danhGiaSanPham = new DanhGiaSanPham();
        danhGiaSanPham.setSao(request.getSao());
        danhGiaSanPham.setBinhLuan(request.getBinhLuan());
        danhGiaSanPham.setNgayTao(LocalDateTime.now());
        danhGiaSanPham.setTrangThai(1);
        danhGiaSanPham.setIdKhachHang(new KhachHang(request.getIdKhachHang())); // Thay đổi để sử dụng ID từ request
        danhGiaSanPham.setIdSanPham(new SanPham(request.getIdSanPham())); // Thay đổi để sử dụng ID từ request

        // Xử lý danh sách hình ảnh từ request
        List<HinhAnhBinhLuan> hinhAnhList = request.getListHinhAnh().stream().map(hinhAnh -> {
            HinhAnhBinhLuan hinhAnhBinhLuan = new HinhAnhBinhLuan();
            hinhAnhBinhLuan.setAnh(hinhAnh);
            hinhAnhBinhLuan.setTrangThai(1);
            hinhAnhBinhLuan.setDanhGiaSanPham(danhGiaSanPham);
            return hinhAnhBinhLuan;
        }).collect(Collectors.toList());
        danhGiaSanPham.setHinhAnhBinhLuan(hinhAnhList);

        // Lưu đánh giá sản phẩm
        DanhGiaSanPham savedDanhGia = danhGiaSanPhamRepository.save(danhGiaSanPham);

        // Cập nhật trung bình đánh giá cho sản phẩm
        updateTrungBinhDanhGia(request.getIdSanPham());

        // Cập nhật trạng thái đã đánh giá cho tất cả chi tiết hóa đơn
        chiTietHoaDons.forEach(chiTiet -> {
            if (!chiTiet.isDanhGia()) { // Chỉ cập nhật nếu chưa đánh giá
                chiTiet.setDanhGia(true);
                hoaDonChiTietRepository.save(chiTiet);
            }
        });

        return savedDanhGia;
    }


    // đánh giá tất cả sản phẩm
    @Transactional
    public List<DanhGiaSanPham> danhGiaTatCaSanPham(Long idKhachHang, DanhGiaSanPhamChungRequest request) {
        // Lấy đối tượng KhachHang từ ID
        KhachHang khachHang = khachHangRepository.findById(idKhachHang)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại")); // Xử lý nếu không tìm thấy khách hàng

        // Lấy các sản phẩm chưa đánh giá của khách hàng
        List<Long> idSanPhams = sanPhamService.laySanPhamChuaDanhGiaCuaKhachHang(idKhachHang);

        // Kiểm tra nếu không có sản phẩm nào để đánh giá
        if (idSanPhams.isEmpty()) {
            return List.of(); // Hoặc ném ngoại lệ tùy theo cách bạn muốn xử lý
        }

        // Tạo danh sách lưu trữ đánh giá
        List<DanhGiaSanPham> savedDanhGiaList = new ArrayList<>();

        // Lặp qua từng sản phẩm để tạo đánh giá
        for (Long idSanPham : idSanPhams) {
            // Tạo một đối tượng đánh giá mới cho mỗi sản phẩm
            DanhGiaSanPham danhGiaSanPham = new DanhGiaSanPham();
            danhGiaSanPham.setSao(request.getSao());
            danhGiaSanPham.setBinhLuan(request.getBinhLuan());
            danhGiaSanPham.setNgayTao(LocalDateTime.now());
            danhGiaSanPham.setTrangThai(1);
            danhGiaSanPham.setIdKhachHang(khachHang); // Sử dụng đối tượng KhachHang
            danhGiaSanPham.setIdSanPham(new SanPham(idSanPham)); // Set ID sản phẩm

            // Lưu đánh giá vào bản đồ
            savedDanhGiaList.add(danhGiaSanPhamRepository.save(danhGiaSanPham));
        }

        // Cập nhật trung bình đánh giá cho sản phẩm
        for (Long idSanPham : idSanPhams) {
            updateTrungBinhDanhGia(idSanPham);
        }

        // Cập nhật trạng thái đã đánh giá cho chi tiết hóa đơn liên quan
        for (Long idSanPham : idSanPhams) {
            List<HoaDonChiTiet> chiTietHoaDons = hoaDonChiTietRepository.findBySanPhamChiTiet_SanPham_Id(idSanPham);
            chiTietHoaDons.forEach(chiTiet -> {
                chiTiet.setDanhGia(true);
                hoaDonChiTietRepository.save(chiTiet);
            });
        }

        return savedDanhGiaList; // Trả về danh sách đánh giá đã lưu
    }

    @Transactional
    public ResponseEntity<?> updateDanhGiaSanPham(DanhGiaSanPhamRequest request) {
        // Tìm đánh giá sản phẩm theo idKhachHang và idSanPham
        Optional<DanhGiaSanPham> optionalDanhGia = danhGiaSanPhamRepository.findById(request.getId());

        if (optionalDanhGia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đánh giá để cập nhật.");
        }

        DanhGiaSanPham danhGiaSanPham = optionalDanhGia.get();

        // Cập nhật thông tin đánh giá
        danhGiaSanPham.setSao(request.getSao());
        danhGiaSanPham.setBinhLuan(request.getBinhLuan());

        // Xóa tất cả các hình ảnh cũ trước khi cập nhật
        hinhAnhBinhLuanRepository.deleteByDanhGiaSanPham_Id(danhGiaSanPham.getId());

        // Thêm các hình ảnh mới từ request
        List<HinhAnhBinhLuan> hinhAnhList = request.getListHinhAnh().stream().map(anh -> {
            HinhAnhBinhLuan hinhAnhComment = new HinhAnhBinhLuan();
            hinhAnhComment.setAnh(anh);
            hinhAnhComment.setTrangThai(1);
            hinhAnhComment.setDanhGiaSanPham(danhGiaSanPham);
            return hinhAnhComment;
        }).collect(Collectors.toList());
        danhGiaSanPham.setHinhAnhBinhLuan(hinhAnhList);

        // Lưu lại đánh giá đã cập nhật
        DanhGiaSanPham updatedDanhGia = danhGiaSanPhamRepository.save(danhGiaSanPham);

        return ResponseEntity.ok(updatedDanhGia);
    }

    @Transactional
    public ResponseEntity<?> deleteDanhGiaSanPham(Long id) {
        Optional<DanhGiaSanPham> optionalDanhGia = danhGiaSanPhamRepository.findById(id);

        if (optionalDanhGia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy đánh giá sản phẩm với ID: " + id);
        }

        // Xóa đánh giá sản phẩm
        danhGiaSanPhamRepository.deleteById(id);
        return ResponseEntity.ok("Đánh giá sản phẩm đã được xóa thành công!");
    }


    public List<SanPhamDanhGiaResponse> laySanPhamCoDanhGia() {
        List<SanPham> sanPhamList = sanPhamRepository.findSanPhamCoDanhGia();

        return sanPhamList.stream().map(sp -> {
            // Lấy số lượng đánh giá theo sao
            Map<Integer, Long> saoThongKe = danhGiaSanPhamRepository.thongKeDanhGiaTheoSao(sp.getId());

            // Đếm số đánh giá có bình luận và hình ảnh
            Long coBinhLuan = danhGiaSanPhamRepository.demDanhGiaCoBinhLuan(sp.getId());
            Long coHinhAnh = danhGiaSanPhamRepository.demDanhGiaCoHinhAnh(sp.getId());

            return new SanPhamDanhGiaResponse(
                    sp.getId(),
                    sp.getTen(),
                    saoThongKe,
                    coBinhLuan,
                    coHinhAnh
            );
        }).collect(Collectors.toList());
    }

    public List<DanhGiaSanPhamResponse> layDanhGiaTheoSanPham(Long idSanPham) {
        List<DanhGiaSanPham> danhGiaSanPhamList = danhGiaSanPhamRepository.findByIdSanPham_Id(idSanPham);

        return danhGiaSanPhamList.stream().map(dg -> new DanhGiaSanPhamResponse(
                dg.getId(),
                dg.getSao(),
                dg.getBinhLuan(),
                dg.getHinhAnhBinhLuan().stream().map(HinhAnhBinhLuan::getAnh).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

}
