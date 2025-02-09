package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.DoiTra.PhieuDoiTra.*;
import datn.be.mycode.response.DoiTra.DetailPhieuDoiTraRes;
import datn.be.mycode.response.DoiTra.HoaDonChiTietDoiTraRes;
import datn.be.mycode.response.DoiTra.KhachHangGetHoaDonChiTietRes;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import datn.be.mycode.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhieuDoiTraService {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PhieuDoiTraRepository phieuDoiTraRepository;
    @Autowired
    private SanPhamDoiTraRepository sanPhamDoiTraRepository;
    @Autowired
    private ThaoTacHoaDonRepository thaoTacHoaDonRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private ThongTinKiemTraRepositpry thongTinKiemTraRepositpry;
    @Autowired
    private ThaoTacPhieuDoiTraRepository thaoTacPhieuDoiTraRepository;
    @Autowired
    private ThongTinLyDoDoiTraRepository thongTinLyDoDoiTraRepository;
    @Autowired
    private PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    public Object getPhieuDoiTra(GetPhieuDoiTraReq request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }
        if (request.getTheLoai() == null) {
            request.setTheLoai(null);
        }
        if (request.getKeyWord() == null || request.getKeyWord().isBlank()){
            request.setKeyWord(null);
        }
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        var phieuGiamGiaPage = phieuDoiTraRepository.findAllByKhachHangAndStatus(request.getKeyWord(), request.getStatus(), request.getTheLoai(), pageable);


        var phieuDoiTraNormalTableResponse = new NormalTableResponse<PhieuDoiTra>();
        phieuDoiTraNormalTableResponse.setItem(phieuGiamGiaPage.getContent());
        phieuDoiTraNormalTableResponse.setPage(request.getPage());
        phieuDoiTraNormalTableResponse.setPageSize(request.getPageSize());
        phieuDoiTraNormalTableResponse.setTotalItem(phieuGiamGiaPage.getTotalElements());
        return phieuDoiTraNormalTableResponse;
    }

    public Object getPhieuDoiTraByKhachHang(GetPhieuDoiTraByKhachHangReq request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        var phieuGiamGiaPage = phieuDoiTraRepository.findAllByKhachHangIdOrderByNgayTaoDesc(request.getIdKhachHang(), pageable);


        var phieuDoiTraNormalTableResponse = new NormalTableResponse<PhieuDoiTra>();
        phieuDoiTraNormalTableResponse.setItem(phieuGiamGiaPage.getContent());
        phieuDoiTraNormalTableResponse.setPage(request.getPage());
        phieuDoiTraNormalTableResponse.setPageSize(request.getPageSize());
        phieuDoiTraNormalTableResponse.setTotalItem(phieuGiamGiaPage.getTotalElements());
        return phieuDoiTraNormalTableResponse;
    }

    public Object getDetailPhieuDoiTra(Long idPhieuDoiTra) {
        var phieuDoiTra = phieuDoiTraRepository.findById(idPhieuDoiTra).orElse(null);
        DetailPhieuDoiTraRes detailPhieuDoiTraRes = new DetailPhieuDoiTraRes();
        if (phieuDoiTra == null) return detailPhieuDoiTraRes;

        var sanPhamDoiTraList = sanPhamDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());
        var thaoTacPhieuDoiTraList = thaoTacPhieuDoiTraRepository.findAllByPhieuDoiTraIdOrderByNgayTaoDesc(phieuDoiTra.getId());
        var khachHangDoiTraThongTinList = thongTinLyDoDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());
        var getThongTinKiemTraList = thongTinKiemTraRepositpry.findAllByPhieuDoiTraId(phieuDoiTra.getId());
        var thongTinKiemTraList = getThongTinKiemTraList.stream().filter(thongTinKiemTra -> thongTinKiemTra.getTrangThai()==1).collect(Collectors.toList());
        var thongTinCuaHangChuyenKhoanList = getThongTinKiemTraList.stream().filter(thongTinKiemTra -> thongTinKiemTra.getTrangThai()==2).collect(Collectors.toList());


        detailPhieuDoiTraRes.setPhieuDoiTra(phieuDoiTra);
        detailPhieuDoiTraRes.setSanPhamDoiTraList(sanPhamDoiTraList);
        detailPhieuDoiTraRes.setThaoTacPhieuDoiTraList(thaoTacPhieuDoiTraList);
        detailPhieuDoiTraRes.setKhachHangDoiTraThongTinList(khachHangDoiTraThongTinList);
        detailPhieuDoiTraRes.setThongTinKiemTraList(thongTinKiemTraList);
        detailPhieuDoiTraRes.setThongTinCuaHangChuyenKhoanList(thongTinCuaHangChuyenKhoanList);
        return detailPhieuDoiTraRes;
    }

    public Object addPhieuDoiTra(AddPhieuDoiTraReq request) {

        String erroMess = validateAddPhieuDoiTra(request);
        if(!erroMess.isBlank()) return getErro(erroMess);
        HoaDon hoaDonCu = hoaDonRepository.findById(request.getIdHoaDon()).orElse(null);
        if(hoaDonCu == null) return getErro("Hóa đơn này không tồn tại");

        String erroSoLuong = CheckSoLuong(hoaDonCu, request);
        if (!erroSoLuong.isBlank()) return getErro(erroSoLuong);

//        San Pham Doi Tra
        List<SanPhamDoiTra> sanPhamDoiTraList = new ArrayList<>();
        for (AddSanPhamDoiTraReq addSanPhamDoiTraReq : request.getSanPhamDoiTraList()) {
            var hoaDonChiTiet = hoaDonChiTietRepository.findById(addSanPhamDoiTraReq.getIdHoaDonChiTiet()).orElse(null);
            if(hoaDonChiTiet == null) return getErro("Hóa đơn chi tiết này không tồn tại");
            var soLuongDoiCuaSanPham = addSanPhamDoiTraReq.getSoLuong();
            if (soLuongDoiCuaSanPham>hoaDonChiTiet.getSoLuong()) return getErro("Không vượt quá số lượng đã mua");
            if (soLuongDoiCuaSanPham <=0) return getErro("số Lượng phải lớn hơn 0");
            if (soLuongDoiCuaSanPham == null) return getErro("Bạn phải nhập số lượng");
            if (!hoaDonCu.equals(hoaDonChiTiet.getHoaDon())) return getErro("hóa đơn không có sản phẩm này");
            SanPhamDoiTra sanPhamDoiTra = new SanPhamDoiTra();
            sanPhamDoiTra.setSoLuong(soLuongDoiCuaSanPham);
            sanPhamDoiTra.setGiaSanPham(hoaDonChiTiet.getGiaDaGiam());
            sanPhamDoiTra.setTrangThai(1);
            sanPhamDoiTra.setSanPham(hoaDonChiTiet.getSanPhamChiTiet());

            sanPhamDoiTra = sanPhamDoiTraRepository.save(sanPhamDoiTra);
            sanPhamDoiTraList.add(sanPhamDoiTra);
        }


        var khachHang = hoaDonCu.getKhachHang();

        PhieuDoiTra phieuDoiTra = new PhieuDoiTra();
//        phieuDoiTra.setSoTaiKhoanNganHang(request.getSoTaiKhoanNganHang());
//        phieuDoiTra.setTenNganHang(request.getTenNganHang());
//        phieuDoiTra.setTenChuTaiKhoan(request.getTenChuTaiKhoan());
        phieuDoiTra.setNgayTao(LocalDateTime.now());
        phieuDoiTra.setLyDoDoiTra(request.getLyDoDoiTra());
        phieuDoiTra.setThaoTac(1);
        phieuDoiTra.setTheLoai(request.getTheLoai());
        phieuDoiTra.setTrangThai(1);
        phieuDoiTra.setHoaDonCu(hoaDonCu);
        phieuDoiTra.setKhachHang(khachHang);

        var phieuDoiTraGet = phieuDoiTraRepository.save(phieuDoiTra);

//        Thong tin ly do doi tra
        request.getHinhAnhList().forEach(s -> {

        ThongTinLyDoDoiTra thongTinLyDoDoiTra = new ThongTinLyDoDoiTra();
        thongTinLyDoDoiTra.setVideo(s);
        thongTinLyDoDoiTra.setTrangThai(1);
        thongTinLyDoDoiTra.setPhieuDoiTra(phieuDoiTraGet);
        thongTinLyDoDoiTraRepository.save(thongTinLyDoDoiTra);
        });

        sanPhamDoiTraList.forEach(sanPhamDoiTra -> {
            sanPhamDoiTra.setPhieuDoiTra(phieuDoiTraGet);
            sanPhamDoiTraRepository.save(sanPhamDoiTra);
        });

        TaoThaoTac(null, khachHang, phieuDoiTraGet);
        TaoThongBao(phieuDoiTra,"Bạn đã tạo phiếu đổi trả");

        phieuDoiTraGet.setMa("PDT"+phieuDoiTraGet.getId());
        return ResponseEntity.ok(phieuDoiTraRepository.save(phieuDoiTraGet));
    }

    private String CheckSoLuong(HoaDon hoaDonCu, AddPhieuDoiTraReq request) {
        var phieuDoiTraList = phieuDoiTraRepository.findByHoaDonCuId(hoaDonCu.getId());
        var hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoaDon_Id(hoaDonCu.getId());

        List<SanPhamDoiTra> sanPhamDoiList = new ArrayList<>();
        phieuDoiTraList.forEach(phieuDoiTra -> {
            var sanPhamDoi = sanPhamDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());
            sanPhamDoiList.addAll(sanPhamDoi);
        });

        var SanPhamAddList = request.getSanPhamDoiTraList();
        boolean check = false;
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {

            // Khởi tạo biến để lưu trữ tổng số lượng
            int totalSoLuongDoi = SanPhamAddList.stream()
                    .filter(addSanPhamDoiTraReq -> addSanPhamDoiTraReq.getIdHoaDonChiTiet() == hoaDonChiTiet.getId())
                    .mapToInt(addSanPhamDoiTraReq -> addSanPhamDoiTraReq.getSoLuong().intValue())
                    .sum();

            // Duyệt qua danh sách sanPhamDoiList để tính tổng số lượng
            for (SanPhamDoiTra sanPhamDoiTra : sanPhamDoiList) {
                if (sanPhamDoiTra.getSanPham().equals(hoaDonChiTiet.getSanPhamChiTiet())) {
                    totalSoLuongDoi += sanPhamDoiTra.getSoLuong(); // Cộng dồn số lượng
                }
            }

            // so so sánh
            if (totalSoLuongDoi > hoaDonChiTiet.getSoLuong()) check = true;
        }
        if (check) return "Số lượng sản phẩm vượt quá số lượng được đổi";
        return "";
    }

    private String validateAddPhieuDoiTra(AddPhieuDoiTraReq request) {
//        if(request.getSoTaiKhoanNganHang().isBlank()) return "Bạn chưa nhập số tài khoản ngân hàng";
//        if(request.getTenChuTaiKhoan().isBlank()) return "Bạn chưa nhập tên chủ tài khoản ngân hàng";
        if(request.getHinhAnhList().isEmpty()) return "Bạn chưa thêm video hoặc ảnh";
        if(request.getLyDoDoiTra().isBlank()) return "Bạn chưa nhập lý do đổi hàng";
        if(request.getTheLoai() == null) return "Bạn chưa chọn thể loại";
        return "";
    }

    public Object xacNhanPhieuDoiTra(XacNhanReq request, int thaoTac) {
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if (nhanVien == null) return getErro("Nhân viên này chưa có tài khoản hoặc đã nghỉ");
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if (phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        if(phieuDoiTra.getTrangThai() != 1) return getErro("Không được thao tác trên phiếu đổi trả này");

        phieuDoiTra.setNhanVien(nhanVien);
        phieuDoiTra.setThaoTac(thaoTac);
        phieuDoiTra.setTrangThai(1);

        ThaoTacPhieuDoiTra thaoTacPhieuDoiTra = new ThaoTacPhieuDoiTra();
        thaoTacPhieuDoiTra.setGhiChu("Xác nhận phiếu");
        thaoTacPhieuDoiTra.setNgayTao(LocalDateTime.now());
        thaoTacPhieuDoiTra.setNhanVien(nhanVien);
        thaoTacPhieuDoiTra.setPhieuDoiTra(phieuDoiTra);
        if (thaoTac == 0) {
            phieuDoiTra.setTrangThai(0);
            thaoTacPhieuDoiTra.setThaoTac("Không chấp nhận phiếu");
        }
        thaoTacPhieuDoiTraRepository.save(thaoTacPhieuDoiTra);
        TaoThongBao(phieuDoiTra,"Phiếu đổi trả của bạn đã được xác nhận");
        emailSender.sendEmailPhieuDoiTra(phieuDoiTra,"Đã được xác nhận");
        return ResponseEntity.ok(phieuDoiTraRepository.save(phieuDoiTra));
    }

    public Object nhanVienKiemTra(KiemTraHangReq request, int trangThaiThongTinKiemTra) {

        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if(nhanVien == null) return getErro("Không tìm thấy nhân viên này");
        var sanPhamDoiTraList = sanPhamDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());

        if(request.getHinhAnhList().isEmpty()) getErro("Chưa có hình ảnh hoặc video");


        request.getHinhAnhList().forEach(s -> {
        ThongTinKiemTra thongTinKiemTra = new ThongTinKiemTra();
            thongTinKiemTra.setPhieuDoiTra(phieuDoiTra);
            thongTinKiemTra.setVideo(s);
            thongTinKiemTra.setTrangThai(trangThaiThongTinKiemTra);
            thongTinKiemTraRepositpry.save(thongTinKiemTra);
        });

        ThaoTacPhieuDoiTra thaoTacPhieuDoiTra = new ThaoTacPhieuDoiTra();
        thaoTacPhieuDoiTra.setGhiChu("Kiểm tra sản phẩm");
        thaoTacPhieuDoiTra.setNgayTao(LocalDateTime.now());
        thaoTacPhieuDoiTra.setNhanVien(nhanVien);
        thaoTacPhieuDoiTra.setPhieuDoiTra(phieuDoiTra);
        thaoTacPhieuDoiTraRepository.save(thaoTacPhieuDoiTra);
        TaoThongBao(phieuDoiTra, "Phiếu đổi trả đã được cập nhật");

        return ResponseEntity.ok(phieuDoiTra);
    }

    public Object nextSetp(NextStepDoiTraReq request) {
        if (request.getIdKhachHang() == null) request.setIdKhachHang(Long.valueOf(0));
        if (request.getIdNhanVien() == null) request.setIdNhanVien(Long.valueOf(0));
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        var khachHang = khachHangRepository.findById(request.getIdKhachHang()).orElse(null);
        if (nhanVien == null && khachHang == null) return getErro("Chưa rõ người thực hiện");
        var trangThaiCu = phieuDoiTra.getTrangThai();

        if (request.getTrangThai() == null) return getErro("Chưa có trạng thái");
        phieuDoiTra.setTrangThai(request.getTrangThai());
        if(phieuDoiTra.getTrangThai()<trangThaiCu) return getErro("Trạng thái phải lớn hơn trạng thái cũ");
        phieuDoiTraRepository.save(phieuDoiTra);

        TaoThaoTac(nhanVien, khachHang, phieuDoiTra);
        TaoThongBao(phieuDoiTra, "Phiếu đổi trả đã được cập nhật");

        return ResponseEntity.ok(phieuDoiTra);
    }

    private void TaoThaoTac(NhanVien nhanVien, KhachHang khachHang, PhieuDoiTra phieuDoiTra) {
        ThaoTacPhieuDoiTra thaoTacPhieuDoiTra = new ThaoTacPhieuDoiTra();
        if (khachHang == null)thaoTacPhieuDoiTra.setGhiChu("Nhân viên: "+ nhanVien.getHoTen()+ " "+ ghiChuNhanVien(phieuDoiTra.getTrangThai()));
        if (nhanVien == null)thaoTacPhieuDoiTra.setGhiChu("Khách hàng: "+ khachHang.getHoTen()+ " "+ ghiChuKhachHang(phieuDoiTra.getTrangThai()));
        thaoTacPhieuDoiTra.setNgayTao(LocalDateTime.now());
        thaoTacPhieuDoiTra.setTrangThai(1);
        thaoTacPhieuDoiTra.setPhieuDoiTra(phieuDoiTra);
        thaoTacPhieuDoiTra.setNhanVien(nhanVien);
        thaoTacPhieuDoiTra.setKhachHang(khachHang);
        thaoTacPhieuDoiTraRepository.save(thaoTacPhieuDoiTra);
    }

    private void TaoThongBao(PhieuDoiTra phieuDoiTra, String conten) {
        ThongBao thongBao = new ThongBao();
        thongBao.setNoiDung(conten);
        thongBao.setUrl("/g4/shop/khach-hang?activeItem=9&idphieu="+phieuDoiTra.getId());
        thongBao.setDaDoc(false);
        thongBao.setNgayTao(LocalDateTime.now());
        thongBao.setTrangThai(1);
        thongBao.setKhachHang(phieuDoiTra.getKhachHang());

        thongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/notifications", phieuDoiTra.getKhachHang().getId());
    }

    private String ghiChuKhachHang(int trangThai) {
        switch (trangThai){
            case 1:
                return "đã tạo phiếu đổi trả";
            case 2:
                return "đã đóng gói và gửi trả sản phẩm";
            case 3:
                return "Đóng gói => Kiểm tra";
            case 4:
                return "Kiểm tra => Cho phép hoàn tiền";
            case 5:
                return "đã xác nhận hoàn thành phiếu trả hàng";
        }
        return null;
    }

    private String ghiChuNhanVien(int trangThai) {
        switch (trangThai){
            case 2:
                return "Mặc định => Đóng gói sản phẩm";
            case 3:
                return "đã kiểm tra sản phảm";
            case 4:
                return "đã xác nhận cho phép hoàn tiền";
            case 5:
                return "đã xác nhận hoàn thành phiếu trả hàng";
        }
        return null;
    }

    public Object addChuyenKhoan(AddChuyenKhoanReq request) {
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");

        String erroMess = validateAddChuyenKhoan(request);
        if(!erroMess.isBlank()) return getErro(erroMess);
        phieuDoiTra.setTenChuTaiKhoan(request.getTenChuTaiKhoan());
        phieuDoiTra.setSoTaiKhoanNganHang(request.getSoTaiKhoanNganHang());
        phieuDoiTra.setTenNganHang(request.getTenNganHang());
        TaoThaoTacPhieuDoiTra(null, phieuDoiTra.getKhachHang(),phieuDoiTra,"đã cập nhật thông tin chuyển khoản");
        TaoThongBao(phieuDoiTra,"Khách hàng cập nhật thông tin chuyển khoản");
        return ResponseEntity.ok(phieuDoiTraRepository.save(phieuDoiTra));

    }

    private String validateAddChuyenKhoan(AddChuyenKhoanReq request) {
        if (request.getSoTaiKhoanNganHang().isBlank()) return "Bạn chưa nhập số tài khoản";
        if (request.getTenChuTaiKhoan().isBlank()) return "Bạn chưa nhập tên chủ tài khoản";
        if (request.getTenNganHang().isBlank()) return "Bạn chưa nhập tên ngân hàng";
        return "";
    }

    public Object taoHoaDon(AddDiaChiReq request) {
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        var sanPhamDoiTraList = sanPhamDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());

        HoaDon hoaDon = TaoHoaDon(request,phieuDoiTra.getKhachHang());
        var hoaDonChiTiet = taoHoaDonChiTiet(hoaDon, sanPhamDoiTraList);

        phieuDoiTra.setHoaDonMoi(hoaDon);
        TaoThaoTacHoaDon(hoaDon);
        TaoThongBaoHoaDon(hoaDon);
        TaoThaoTacPhieuDoiTra(null, phieuDoiTra.getKhachHang(),phieuDoiTra,"đã nhập thông tin địa chỉ và tạo thành công hóa đơn");

        return ResponseEntity.ok(phieuDoiTra);
    }

    private void TaoThongBaoHoaDon(HoaDon hoaDon) {
        ThongBao thongBao = new ThongBao();
        thongBao.setNoiDung("Bạn đã đặt thành công đơn hàng "+hoaDon.getMa());
        thongBao.setUrl("/g4/shop/khach-hang?activeItem=1&idHoaDon="+hoaDon.getId());
        thongBao.setDaDoc(false);
        thongBao.setNgayTao(LocalDateTime.now());
        thongBao.setTrangThai(1);
        thongBao.setKhachHang(hoaDon.getKhachHang());

        thongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/notifications", hoaDon.getKhachHang().getId());
    }

    private void TaoThaoTacHoaDon(HoaDon hoaDon) {
        ThaoTacHoaDon thaoTacHoaDon = new ThaoTacHoaDon();
        thaoTacHoaDon.setThaoTac("Tạo thành công hóa đơn đổi trả");
        thaoTacHoaDon.setNgayTao(LocalDateTime.now());
        thaoTacHoaDon.setTrangThai(1);
        thaoTacHoaDon.setHoaDon(hoaDon);
        thaoTacHoaDonRepository.save(thaoTacHoaDon);

    }

    private List<HoaDonChiTiet> taoHoaDonChiTiet(HoaDon hoaDon, List<SanPhamDoiTra> sanPhamDoiTraList) {
        List<HoaDonChiTiet>  hoaDonChiTiets = new ArrayList<>();
        sanPhamDoiTraList.forEach(sanPhamDoiTra -> {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setSoLuong(sanPhamDoiTra.getSoLuong());
            hoaDonChiTiet.setGiaHienHanh(sanPhamDoiTra.getGiaSanPham());
            hoaDonChiTiet.setGiaDaGiam(sanPhamDoiTra.getGiaSanPham());
            hoaDonChiTiet.setTrangThai(1);
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamDoiTra.getSanPham());
            hoaDonChiTiet.setDanhGia(false);

            hoaDonChiTiet = hoaDonChiTietRepository.save(hoaDonChiTiet);
            hoaDonChiTiets.add(hoaDonChiTiet);
        });
        return hoaDonChiTiets;
    }

    private HoaDon TaoHoaDon(AddDiaChiReq request, KhachHang khachHang) {
        var gia = BigDecimal.valueOf(0);
        var pttt = phuongThucThanhToanRepository.findById(Long.valueOf(3)).orElse(null);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setGhiChu(request.getGhiChu());
        hoaDon.setTenNguoiNhan(request.getTenNguoiNhan());
        hoaDon.setSdtNguoiNhan(request.getSdtNguoiNhan());
        hoaDon.setIdTinhThanh(request.getIdTinhThanh());
        hoaDon.setTinhThanh(request.getTinhThanh());
        hoaDon.setIdQuanHuyen(request.getIdQuanHuyen());
        hoaDon.setQuanHuyen(request.getQuanHuyen());
        hoaDon.setIdPhuongXa(request.getIdPhuongXa());
        hoaDon.setPhuongXa(request.getPhuongXa());
        hoaDon.setPhiShip(gia);
        hoaDon.setTongTien(gia);
        hoaDon.setTongTienSanPhamChuaGiam(gia);
        hoaDon.setTongTienGiam(gia);
        hoaDon.setPhuongThucThanhToan(pttt);
        hoaDon.setThanhToan(false);
        hoaDon.setGiaoHang(true);
        hoaDon.setTrangThai(2);
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(null);

        hoaDon = hoaDonRepository.save(hoaDon);
        hoaDon.setMa("HDDT"+hoaDon.getId());

        return hoaDonRepository.save(hoaDon);
    }

    public Object getHoaDonChiTietChoViewKhachHang(Long idHoaDon) {
        var hoaDon = hoaDonRepository.findById(idHoaDon).orElse(null);
        if(hoaDon == null)return null;
        var phieuDoiTraList = phieuDoiTraRepository.findByHoaDonCuId(hoaDon.getId());
        List<SanPhamDoiTra> sanPhamDoiList = new ArrayList<>();
        phieuDoiTraList.forEach(phieuDoiTra -> {
            var sanPhamDoi = sanPhamDoiTraRepository.findAllByPhieuDoiTraId(phieuDoiTra.getId());
            sanPhamDoiList.addAll(sanPhamDoi);
        });

        var hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoaDon_Id(hoaDon.getId());

        List<HoaDonChiTietDoiTraRes> hoaDonChiTietRes = new ArrayList<>();
        hoaDonChiTietList.forEach(hoaDonChiTiet -> {
            HoaDonChiTietDoiTraRes hoaDonChiTietDoiTraRes = new HoaDonChiTietDoiTraRes();
            hoaDonChiTietDoiTraRes.HoaDonChiTietDoiTraRes(hoaDonChiTiet);
            // Khởi tạo biến để lưu trữ tổng số lượng
            int totalSoLuongDoi = 0;

            // Duyệt qua danh sách sanPhamDoiList để tính tổng số lượng
            for (SanPhamDoiTra sanPhamDoiTra : sanPhamDoiList) {
                if (sanPhamDoiTra.getSanPham().equals(hoaDonChiTiet.getSanPhamChiTiet())) {
                    totalSoLuongDoi += sanPhamDoiTra.getSoLuong(); // Cộng dồn số lượng
                }
            }

            // Cập nhật số lượng vào hoaDonChiTietDoiTraRes
            hoaDonChiTietDoiTraRes.setSoLuongDoi(totalSoLuongDoi);
            hoaDonChiTietRes.add(hoaDonChiTietDoiTraRes);
        });

        var thaoTacHoaDonList = thaoTacHoaDonRepository.findAllByHoaDonId(hoaDon.getId());

        KhachHangGetHoaDonChiTietRes khachHangGetHoaDonChiTietRes = new KhachHangGetHoaDonChiTietRes();
        khachHangGetHoaDonChiTietRes.setHoaDon(hoaDon);
        khachHangGetHoaDonChiTietRes.setHoaDonChiTietList(hoaDonChiTietRes);
        khachHangGetHoaDonChiTietRes.setThaoTacHoaDons(thaoTacHoaDonList);
        return khachHangGetHoaDonChiTietRes;
    }

    public Object nhanVienNote(NhanVienNoteReq request) {
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if (nhanVien == null) return getErro("Không tìm thấy nhân viên này");
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        if (request.getGhiChu().isBlank()) getErro("Chưa nhập gì");

        TaoThaoTacPhieuDoiTra(nhanVien, null, phieuDoiTra, request.getGhiChu());
        return ResponseEntity.ok(phieuDoiTra);
    }

    public Object huyPhieuDoiTra(HuyPhieuDoiTraReq request) {
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if (nhanVien == null) return getErro("Không tìm thấy nhân viên này");
        var phieuDoiTra = phieuDoiTraRepository.findById(request.getIdPhieuDoiTra()).orElse(null);
        if(phieuDoiTra == null) return getErro("Không tìm thấy phiếu đổi trả này");
        if (request.getGhiChu().isBlank()) getErro("Chưa nhập gì");

        phieuDoiTra.setGhiChuNhanVien(request.getGhiChu());
        phieuDoiTra.setThaoTac(0);
        phieuDoiTra.setTrangThai(0);
        phieuDoiTraRepository.save(phieuDoiTra);

        TaoThaoTacPhieuDoiTra(nhanVien, null, phieuDoiTra, request.getGhiChu());
        TaoThongBao(phieuDoiTra,"Phiếu đổi trả của bạn đã bị hủy");
        emailSender.sendEmailPhieuDoiTra(phieuDoiTra,"Phiếu đổi trả của bạn đã bị hủy");
        return ResponseEntity.ok(phieuDoiTra);
}

    private void TaoThaoTacPhieuDoiTra(NhanVien nhanVien, KhachHang khachHang, PhieuDoiTra phieuDoiTra,String conten) {
        ThaoTacPhieuDoiTra thaoTacPhieuDoiTra = new ThaoTacPhieuDoiTra();
        if (khachHang == null)thaoTacPhieuDoiTra.setGhiChu("Nhân viên: "+ nhanVien.getHoTen()+ " "+conten);
        if (nhanVien == null)thaoTacPhieuDoiTra.setGhiChu("Khách hàng: "+ khachHang.getHoTen()+ " "+conten);
        thaoTacPhieuDoiTra.setNgayTao(LocalDateTime.now());
        thaoTacPhieuDoiTra.setTrangThai(1);
        thaoTacPhieuDoiTra.setPhieuDoiTra(phieuDoiTra);
        thaoTacPhieuDoiTra.setNhanVien(nhanVien);
        thaoTacPhieuDoiTra.setKhachHang(khachHang);
        thaoTacPhieuDoiTraRepository.save(thaoTacPhieuDoiTra);
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

}
