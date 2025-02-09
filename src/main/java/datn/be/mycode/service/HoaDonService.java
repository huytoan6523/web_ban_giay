package datn.be.mycode.service;

import datn.be.mycode.entity.*;
import datn.be.mycode.repository.*;
import datn.be.mycode.request.HoaDon.HoaDonByKhachHang;
import datn.be.mycode.request.HoaDon.HuyHoaDonRequest;
import datn.be.mycode.request.HoaDon.NextStep;
import datn.be.mycode.request.HoaDonRequest;
import datn.be.mycode.request.VoucherHoaDonRequest;
import datn.be.mycode.request.customRequest.BanHang.*;
import datn.be.mycode.request.customRequest.TableHoaDonRequest;
import datn.be.mycode.request.thaoTacHoaDon.ThaoTacHoaDonAddRequest;
import datn.be.mycode.response.ThongKe.DoanhSo;
import datn.be.mycode.response.ThongKe.DoanhSoThang;
import datn.be.mycode.response.ThongKe.Top5SanPham;
import datn.be.mycode.response.hoaDon.HoaDonChiTietChoViewKhachHang;
import datn.be.mycode.response.hoaDon.HoaDonResponse;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import datn.be.mycode.response.hoaDon.HoaDonThanhToanXong;
import datn.be.mycode.util.EmailSender;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository repo;
    @Autowired
    private ThaoTacHoaDonService thaoTacHoaDonService;
    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private PhuongThucThanhToanRepository phuongThucThanhToanRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherHoaDonService voucherHoaDonService;
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    private ThaoTacHoaDonRepository thaoTacHoaDonRepository;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private ThongBaoRepository thongBaoRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    public NormalTableResponse<HoaDonResponse> getAllByTrangThai1(TableHoaDonRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<HoaDon> hoaDonResponsePage;
        hoaDonResponsePage = repo.searchAll(request.getKeyWord(), request.getStartDate(), request.getEndDate(), request.getIdKhachHang(), request.getIdNhanVien(), 1, pageable);

        List<HoaDon> hoaDons = hoaDonResponsePage.getContent();
        List<HoaDonResponse> hoaDonResponses = new ArrayList<>();

        for (HoaDon hoaDon :hoaDons) {
            List<Voucher> vouchers = voucherHoaDonService.getVouchersByHoaDonId(hoaDon.getId());
            HoaDonResponse hoaDonResponse = new HoaDonResponse(hoaDon,vouchers);
            hoaDonResponses.add(hoaDonResponse);
        }

        var hoaDonNormalTableResponse = new NormalTableResponse<HoaDonResponse>(hoaDonResponses,request.getPage(),request.getPageSize(),hoaDonResponsePage.getTotalElements());


        return hoaDonNormalTableResponse;
    }

    public NormalTableResponse<HoaDonResponse> getAll(TableHoaDonRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        if (request.getStatus() == null) {
            request.setStatus(1);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<HoaDon> hoaDonResponsePage;
        hoaDonResponsePage = repo.searchAll(request.getKeyWord(), request.getStartDate(), request.getEndDate(), request.getIdKhachHang(), request.getIdNhanVien(), request.getStatus(), pageable);

        List<HoaDon> hoaDons = hoaDonResponsePage.getContent();
        List<HoaDonResponse> hoaDonResponses = new ArrayList<>();

        for (HoaDon hoaDon :hoaDons) {
            List<Voucher> vouchers = voucherHoaDonService.getVouchersByHoaDonId(hoaDon.getId());
            HoaDonResponse hoaDonResponse = new HoaDonResponse(hoaDon,vouchers);
            hoaDonResponses.add(hoaDonResponse);
        }

        var hoaDonNormalTableResponse = new NormalTableResponse<HoaDonResponse>(hoaDonResponses,request.getPage(),request.getPageSize(),hoaDonResponsePage.getTotalElements());


        return hoaDonNormalTableResponse;
    }

    public ResponseEntity<?> getById(Long idHoaHon){
        var hoaDon = repo.findById(idHoaHon).orElse(null);
        if (hoaDon == null ) return getErro("Hóa đơn này không tồn tại");
        var hoaDonChiTiets = hoaDonChiTietService.getListByHoaDonId(hoaDon.getId());


        var voucher = voucherHoaDonService.getVouchersByHoaDonId(hoaDon.getId());
        HoaDonThanhToanXong hoaDonResponse = new HoaDonThanhToanXong(hoaDon,voucher,hoaDonChiTiets);

        return ResponseEntity.ok(hoaDonResponse);
    }

    public HoaDon add(HoaDonRequest request){

        HoaDon hoaDon = new HoaDon().builder()
                .ma(request.getMa())
                .ngayTao(request.getNgayTao())
                .ngayThanhToan(request.getNgayThanhToan())
                .ghiChu(request.getGhiChu())
                .tenNguoiNhan(request.getTenNguoiNhan())
                .sdtNguoiNhan(request.getSdtNguoiNhan())
                .phiShip(request.getPhiShip())
                .tongTien(request.getTongTien())
                .tongTienSanPhamChuaGiam(request.getTongTienSanPhamChuaGiam())
                .tongTienGiam(request.getTongTienGiam())
                .giaoHang(false)
                .thanhToan(false)
                .phuongThucThanhToan(phuongThucThanhToanRepository.findById(request.getIdPhuongThucThanhToan()).get())
                .nhanVien(nhanVienRepository.findById(request.getIdNhanVien()).get())
                .khachHang(khachHangRepository.findById(request.getIdKhachHang()).get())
                .build();

        HoaDon hoaDonRes = repo.save(hoaDon);
        hoaDonRes.setMa("HD"+hoaDonRes.getId());
        ThaoTacHoaDonAddRequest thaoTacHoaDon = new ThaoTacHoaDonAddRequest().builder()
                .thaoTac("add")
                .idHoaDon(hoaDonRes.getId())
                .idNhanVien(hoaDon.getNhanVien().getId())
                .build();

        thaoTacHoaDonService.add(thaoTacHoaDon);

        return hoaDonRes;
    }
    public HoaDon add(Long idNhanVien){
        NhanVien nhanVien = nhanVienRepository.findById(idNhanVien).orElse(null);
        if(nhanVien != null){
        HoaDon hoaDon = new HoaDon().builder()
                .ngayTao(LocalDateTime.now())
                .phiShip(BigDecimal.valueOf(0))
                .tongTien(BigDecimal.valueOf(0))
                .tongTienSanPhamChuaGiam(BigDecimal.valueOf(0))
                .tongTienGiam(BigDecimal.valueOf(0))
                .giaoHang(false)
                .thanhToan(false)
                .phuongThucThanhToan(phuongThucThanhToanRepository.findById(Long.valueOf(1)).get())
                .nhanVien(nhanVien)
                .trangThai(1)
                .build();

        HoaDon hoaDonRes = repo.save(hoaDon);
        hoaDonRes.setMa("HD" + hoaDonRes.getId());
        ThaoTacHoaDonAddRequest thaoTacHoaDon = new ThaoTacHoaDonAddRequest().builder()
                    .thaoTac("Tạo hóa đơn")
                    .idHoaDon(hoaDonRes.getId())
                    .idNhanVien(hoaDon.getNhanVien().getId())
                    .build();

        thaoTacHoaDonService.add(thaoTacHoaDon);

        return repo.save(hoaDonRes);
    }
        return null;
    }

    public HoaDon update(HoaDonRequest request){

        HoaDon hoaDon = new HoaDon().builder()
                .ma(request.getMa())
                .ngayThanhToan(request.getNgayThanhToan())
                .ghiChu(request.getGhiChu())
                .tenNguoiNhan(request.getTenNguoiNhan())
                .sdtNguoiNhan(request.getSdtNguoiNhan())
                .phiShip(request.getPhiShip())
                .tongTien(request.getTongTien())
                .tongTienSanPhamChuaGiam(request.getTongTienSanPhamChuaGiam())
                .tongTienGiam(request.getTongTienGiam())
                .phuongThucThanhToan(phuongThucThanhToanRepository.findById(request.getIdPhuongThucThanhToan()).get())
                .nhanVien(nhanVienRepository.findById(request.getIdNhanVien()).get())
                .khachHang(khachHangRepository.findById(request.getIdKhachHang()).get())
                .build();

        HoaDon hoaDonRes = repo.save(hoaDon);
        ThaoTacHoaDonAddRequest thaoTacHoaDon = new ThaoTacHoaDonAddRequest().builder()
                .thaoTac("update")
                .idHoaDon(hoaDonRes.getId())
                .idNhanVien(hoaDon.getNhanVien().getId())
                .build();

        thaoTacHoaDonService.add(thaoTacHoaDon);

        return hoaDonRes;
    }

    public HoaDon udateTrangThai(Long id, Integer status){
        HoaDon hoaDon = repo.findById(id).get();
        hoaDon.setTrangThai(status);

        HoaDon hoaDonRes = repo.save(hoaDon);
        ThaoTacHoaDonAddRequest thaoTacHoaDon = new ThaoTacHoaDonAddRequest().builder()
                .thaoTac("delete")
                .idHoaDon(hoaDonRes.getId())
                .idNhanVien(hoaDon.getNhanVien().getId())
                .build();

        thaoTacHoaDonService.add(thaoTacHoaDon);

        return hoaDonRes;
    }

    public ResponseEntity<?> hoaDonAddDiaChi(AddDiaChi request) {
        var hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);

        if (hoaDon != null){

            if(checkDiaChi(request)){
                getDiaChi(hoaDon, request);
                hoaDon.setTenNguoiNhan(request.getTenNguoiNhan());
                hoaDon.setSdtNguoiNhan(request.getSdtNguoiNhan());
                return ResponseEntity.ok(repo.save(hoaDon));
            }
            return getErro("Địa chỉ hoặc thông tin người nhận trống");
        }

        return getErro("Hóa đơn không tồn tại");
    }

    private boolean checkDiaChi(AddDiaChi request) {
        boolean check = false;
        if (!request.getGhiChu().trim().isEmpty()){
            check =true;
        }
        if (!request.getTenNguoiNhan().trim().isEmpty()){
            check =true;
        }
        if (!request.getSdtNguoiNhan().trim().isEmpty()){
            check =true;
        }
        return check;
    }

    private void getDiaChi(HoaDon hoaDon, AddDiaChi request) {
        hoaDon.setIdTinhThanh(request.getIdTinhThanh());
        hoaDon.setTinhThanh(request.getTinhThanh());
        hoaDon.setIdQuanHuyen(request.getIdQuanHuyen());
        hoaDon.setQuanHuyen(request.getQuanHuyen());
        hoaDon.setIdPhuongXa(request.getIdPhuongXa());
        hoaDon.setPhuongXa(request.getPhuongXa());
        hoaDon.setGhiChu(request.getGhiChu());
    }

    public ResponseEntity<?> hoaDonAddKhachHang(AddObject request) {
        var hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        var khachHang = khachHangRepository.findById(request.getIdObject()).orElse(null);

        if (hoaDon != null){
            if(khachHang != null) {
                hoaDon.setKhachHang(khachHang);
                return ResponseEntity.ok(repo.save(hoaDon));
            }
            return getErro("Khách hàng không tồn tại");
        }
        return getErro("Hóa đơn không tồn tại");
    }

    public ResponseEntity<?> hoaDonAddVoucher(AddVoucher request) {

        var hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        if (hoaDon == null) return getErro("Hóa đơn không tồn tại");

        var idVoucher = request.getIdVoucher();

        var voucher = voucherRepository.findById(request.getIdVoucher()).orElse(null);
        if(voucher == null || voucher.getTrangThai() != 1) {
            return getErro("không tìm thấy voucher hoặc voucher không hoạt động");
        }
        if(voucher.getSoLuong() <= 0){
            return getErro("Voucher này đã hết");
        }
        var voucherHoaDons = voucherHoaDonService.getVoucherHoaDonByHoaDon(hoaDon.getId());

//        check xem hoaDon có voucher này chưa
        boolean addNewVoucher = true;
        for (VoucherHoaDon voucherHoaDon:voucherHoaDons) {
            if(voucherHoaDon.getVoucher().getTheLoai() == voucher.getTheLoai()){
                voucherHoaDon.setVoucher(voucher);
                var updateVoucher = new VoucherHoaDonRequest(voucherHoaDon.getId(),voucherHoaDon.getNgayTao(),voucherHoaDon.getNgaySua(),voucherHoaDon.getTrangThai(),voucherHoaDon.getVoucher().getId(),voucherHoaDon.getHoaDon().getId());
                voucherHoaDonService.update(updateVoucher);
                addNewVoucher = false;
            }
        }
        if(addNewVoucher) {
            var addVoucher = new VoucherHoaDonRequest(voucher.getId(), hoaDon.getId());
            voucherHoaDonService.add(addVoucher);
        }

        return ResponseEntity.ok(hoaDon);
    }

    public ResponseEntity<?> hoaDonAddPhuongThucThanhToan(AddObject request) {
        var hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        var phuongThucThanhToan = phuongThucThanhToanRepository.findById(request.getIdObject()).orElse(null);

        if (hoaDon != null){
            if(phuongThucThanhToan != null) {
                hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
                return ResponseEntity.ok(repo.save(hoaDon));
            }
            return getErro("Phương thức thanh toán không tồn tại");
        }
        return getErro("Hóa đơn không tồn tại");
    }

    public ResponseEntity<?> isGiaoHangForHoaDon(updateGiaoHang request) {
        var hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);

        if (hoaDon == null){
            return getErro("Hóa đơn không tồn tại");
        }

        hoaDon.setGiaoHang(request.getGiaohang());

        return ResponseEntity.ok(repo.save(hoaDon));
    }

    public ResponseEntity<?> deleteVoucher(DeleteVoucher request) {

        HoaDon hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        if(hoaDon == null) return getErro("Hóa đơn không tồn tại");
//        kiểm tra hoa don da thanh toan hay chua
        if (hoaDon.getTrangThai() == 6) return getErro("Hóa đơn này không được thay đổi");
        voucherHoaDonService.delete(request.getIdHoaDon(),request.getIdVoucher());
        return ResponseEntity.ok(hoaDon);
    }

    public ResponseEntity<?> thanhToan(ThanhToanRequest request) {
        HoaDon hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        if(hoaDon == null) return getErro("Hóa đơn này không tồn tại");
        if(checkVoucherForHoaDon(hoaDon)) return getErro("Có voucher đã hết hạn");

        String messCheckSoLuong = hoaDonChiTietService.checkSoLuong(hoaDon.getId());
        if(messCheckSoLuong != null) getErro(messCheckSoLuong);

        setHoaDon(hoaDon, request);
        hoaDon.setThanhToan(true);

//        thao tac hoa don khi thanh toan
        var thaoTacHoaDon = new ThaoTacHoaDonAddRequest();
        thaoTacHoaDon.setThaoTac(getStringThaoTac(hoaDon.getTrangThai()));
        thaoTacHoaDon.setIdHoaDon(hoaDon.getId());
        thaoTacHoaDon.setIdNhanVien(Long.valueOf(3));

//        kien tra loai thanh toan
        var phuongThucThanhToan = hoaDon.getPhuongThucThanhToan();
        if(phuongThucThanhToan.getId() == 2 ){
            hoaDon.setThanhToan(false);
            repo.save(hoaDon);

            return ResponseEntity.ok(taoThanhToanOnline(hoaDon.getTongTien().intValue(), hoaDon.getId()+""));
        }
        if(phuongThucThanhToan.getId() == 3 ){
            if(hoaDon.getGiaoHang()) {
                hoaDon.setThanhToan(false);
                hoaDon.setTrangThai(2);
//                setSoLuongSanPham(hoaDon);
                setVoucher(hoaDon);
                repo.save(hoaDon);
                thaoTacHoaDonService.add(thaoTacHoaDon);
                return ResponseEntity.ok("Thành công");
            }
        }
//        subtract quantity pruduct
        if(hoaDon.getTrangThai() == 6 || hoaDon.getThanhToan() == true){
//            setSoLuongSanPham(hoaDon);
            setVoucher(hoaDon);
            hoaDonChiTietService.setTrangThaiByHoaDonDone(hoaDon);
        }
        hoaDon.setNgayThanhToan(LocalDateTime.now());
        repo.save(hoaDon);
        thaoTacHoaDonService.add(thaoTacHoaDon);

        if(hoaDon.getKhachHang().getEmail() != null) {
            String thongBao = "Bạn đã đặt thành công đơn hàng "+hoaDon.getMa();
            TaoThongBao(hoaDon,thongBao);
            emailSender.sendEmailHoaDon(hoaDon.getKhachHang().getEmail(),hoaDonChiTietService.getListByHoaDonId(hoaDon.getId()),hoaDon);
        }
        return ResponseEntity.ok("Thành công");
    }

    private void setVoucher(HoaDon hoaDon) {
        var voucherHoaDons = voucherHoaDonService.getVoucherHoaDonByHoaDon(hoaDon.getId());
        for (VoucherHoaDon voucherHoaDon:voucherHoaDons) {
            var voucher = voucherHoaDon.getVoucher();
            voucher.setSoLuong(voucher.getSoLuong()-1);
            voucherRepository.save(voucher);
        }
    }

    private String setSoLuongSanPham(HoaDon hoaDon) {
        String erroMess = hoaDonChiTietService.setSoLuongAfterPayment(hoaDon.getId());
        if (erroMess.isBlank() == false) return erroMess;

        return "";
    }

    private void setHoaDon(HoaDon hoaDon, ThanhToanRequest request) {
        hoaDon.setPhiShip(hoaDon.getGiaoHang() == true ? request.getPhiShip() : BigDecimal.valueOf(0));
        hoaDon.setTongTien(request.getTongTien());
        hoaDon.setTongTienSanPhamChuaGiam(request.getTongTienSanPhamChuaGiam());
        hoaDon.setTongTienGiam(request.getTongTienGiam());
        hoaDon.setTrangThai(request.getTrangThai());

    }

    private boolean checkVoucherForHoaDon(HoaDon hoaDon) {
        List<VoucherHoaDon> voucherHoaDonByHoaDons = voucherHoaDonService.getVoucherHoaDonByHoaDon(hoaDon.getId());
        LocalDateTime now = LocalDateTime.now();

        for (VoucherHoaDon voucherHoaDon:voucherHoaDonByHoaDons){

            LocalDateTime endDay = voucherHoaDon.getVoucher().getNgayKetThuc();
            if (endDay.isBefore(now)) {
                // Voucher đã quá hạn
                return false;
            }
            if (voucherHoaDon.getVoucher().getSoLuong()<=0) {
                // Voucher đã quá hạn
                return false;
            }
        }

        return false;
    }


    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

    public String taoThanhToanOnline(int orderTotal,String maHoaDon) {
        return vnPayService.createOrder(orderTotal,maHoaDon,"http://localhost:4200/auth/admin/ban-hang-tai-quay");

    }

    public ResponseEntity<?> updateTrangThaiThanhToan(Long idHoaDon) {
        HoaDon hoaDon = repo.findById(idHoaDon).orElse(null);
        if(hoaDon == null) return getErro("Hóa đơn không tồn tại");

//        thao tac hoa don khi thanh toan
        var thaoTacHoaDon = new ThaoTacHoaDonAddRequest();
        thaoTacHoaDon.setThaoTac(getStringThaoTac(hoaDon.getTrangThai()));
        thaoTacHoaDon.setIdHoaDon(hoaDon.getId());
        thaoTacHoaDon.setIdNhanVien(Long.valueOf(3));

        hoaDon.setThanhToan(true);
        hoaDon.setTrangThai(2);
        hoaDon.setNgayThanhToan(LocalDateTime.now());
//        setSoLuongSanPham(hoaDon);
        setVoucher(hoaDon);
        repo.save(hoaDon);
        thaoTacHoaDonService.add(thaoTacHoaDon);
        hoaDonChiTietService.setTrangThaiByHoaDonDone(hoaDon);

        if(hoaDon.getKhachHang().getEmail() != null) {
            String thongBao = "Bạn đã đặt thành công đơn hàng "+hoaDon.getMa();
            TaoThongBao(hoaDon,thongBao);
            emailSender.sendEmailHoaDon(hoaDon.getKhachHang().getEmail(),hoaDonChiTietService.getListByHoaDonId(hoaDon.getId()),hoaDon);
        }
        return ResponseEntity.ok("thành công");
    }

    public ResponseEntity<?> nextSetp(NextStep request) {
        HoaDon hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        var nhanVien = nhanVienRepository.findById(request.getIdNhanVien()).orElse(null);
        if(hoaDon == null) return getErro("Hóa đơn này không tồn tại");
        if(hoaDon.getTrangThai() == 6) return getErro("Hóa đơn này đã hoàn thành không để chỉnh sửa");
        if(hoaDon.getTrangThai() == 5) {
            if(hoaDon.getPhuongThucThanhToan().getId() == 3) {

                hoaDon.setNgayThanhToan(LocalDateTime.now());
                hoaDon.setThanhToan(true);
//                if (hoaDon.getNhanVien() == null){
//                    var erroMess = setSoLuongSanPham(hoaDon);
//                    if (erroMess.isBlank() ==false) return getErro(erroMess);
//                }
                hoaDonChiTietService.setTrangThaiByHoaDonDone(hoaDon);
            }
        }
        if(hoaDon.getTrangThai() ==2 && hoaDon.getNhanVien() == null){
            var erroMess = setSoLuongSanPham(hoaDon);
            if (erroMess.isBlank() ==false) return getErro(erroMess);
        };
        var thaoTacHoaDon = new ThaoTacHoaDonAddRequest();

        thaoTacHoaDon.setThaoTac(getStringThaoTac(hoaDon.getTrangThai()));
        thaoTacHoaDon.setIdHoaDon(hoaDon.getId());
        thaoTacHoaDon.setIdNhanVien(nhanVien.getId());


        var trangThai = hoaDon.getTrangThai();
        if(trangThai == 2) trangThai += 1;
        trangThai += 1;
        hoaDon.setTrangThai(trangThai);
        hoaDon = repo.save(hoaDon);
        thaoTacHoaDonService.add(thaoTacHoaDon);

        if(hoaDon.getKhachHang().getEmail() != null && trangThai != 6) {
            String thongBao = "Hóa đơn "+hoaDon.getMa()+": "+thaoTacHoaDon.getThaoTac();
            TaoThongBao(hoaDon,thongBao);
            emailSender.sendEmailTrangThaiHoaDon(hoaDon.getKhachHang().getEmail(),thaoTacHoaDon);
        }
        if(hoaDon.getKhachHang().getEmail() != null && trangThai == 6) {
            String thongBao = "Bạn đã đặt thành công đơn hàng "+hoaDon.getMa();
            TaoThongBao(hoaDon,thongBao);
//            emailSender.sendEmailHoaDon(hoaDon.getKhachHang().getEmail(),hoaDonChiTietService.getListByHoaDonId(hoaDon.getId()),hoaDon);
        }
        return ResponseEntity.ok("ThanhCong");
    }

    private void TaoThongBao(HoaDon hoaDon, String conten) {
        ThongBao thongBao = new ThongBao();
        thongBao.setNoiDung(conten);
        thongBao.setUrl("/g4/shop/khach-hang?activeItem=1&idHoaDon="+hoaDon.getId());
        thongBao.setDaDoc(false);
        thongBao.setNgayTao(LocalDateTime.now());
        thongBao.setTrangThai(1);
        thongBao.setKhachHang(hoaDon.getKhachHang());

        thongBaoRepository.save(thongBao);
        messagingTemplate.convertAndSend("/topic/notifications", hoaDon.getKhachHang().getId());
    }

    private String getStringThaoTac(int trangThai){
        switch (trangThai){
            case 1:
                return "Hóa đơn khởi tạo => Hóa đơn chờ sử lý";
            case 2:
                return "Hóa đơn chờ sử lý => Hóa đơn đã nhận";
            case 3:
                return "Hóa đơn đã nhận => Đóng gói hóa đơn";
            case 4:
                return "Đóng gói hóa đơn => Hóa đơn vận chuyển";
            case 5:
                return "Hóa đơn vận chuyển => Hóa đơn hoàn thành";
        }
        return null;
    }

    public ResponseEntity<?> huyHoaDon(HuyHoaDonRequest request) {

        HoaDon hoaDon = repo.findById(request.getIdHoaDon()).orElse(null);
        if(hoaDon == null) return getErro("Hóa đơn này không tồn tại");
        var trangThai = hoaDon.getTrangThai();
        if(trangThai == 6) return getErro("Hóa đơn này đã hoàn thành không để chỉnh sửa");
        if(trangThai == 0) return getErro("Hóa đơn này đã hủy");
//        if(trangThai != 2) return getErro("Hóa đơn này đã xác nhận không được hủy");

        var thaoTacHoaDon = new ThaoTacHoaDonAddRequest();
        switch (trangThai){
            case 1:
                thaoTacHoaDon.setThaoTac("(Hóa đơn khởi tạo => Hóa đơn đã hủy)( "+request.getGhiChu()+")");
                break;
            case 2:
                thaoTacHoaDon.setThaoTac("(Hóa đơn chờ sử lý => Hóa đơn đã hủy)( "+request.getGhiChu()+")");
                break;
            case 3:
                thaoTacHoaDon.setThaoTac("(Hóa đơn đã nhận => Hóa đơn đã hủy)( "+request.getGhiChu()+")");
                break;
            case 4:
                thaoTacHoaDon.setThaoTac("(Đóng gói hóa đơn => Hóa đơn đã hủy)( "+request.getGhiChu()+")");
                break;
            case 5:
                thaoTacHoaDon.setThaoTac("(Hóa đơn vận chuyển => Hóa đơn đã hủy)( "+request.getGhiChu()+")");
                break;
        }

        thaoTacHoaDon.setIdHoaDon(hoaDon.getId());
        thaoTacHoaDon.setIdNhanVien(request.getIdNhanVien());

        hoaDon.setTrangThai(0);

        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietService.getListByHoaDonId(hoaDon.getId());
        if(trangThai > 2){
            for (HoaDonChiTiet hoaDonChiTiet: hoaDonChiTiets) {
                var soLuongBan = hoaDonChiTiet.getSoLuong();
                var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
                var soLuongSauHuy = sanPhamChiTiet.getSoLuong() + soLuongBan;
                sanPhamChiTiet.setSoLuong(soLuongSauHuy);

                sanPhamChiTietRepository.save(sanPhamChiTiet);
            }
        }
        if(trangThai == 2 && hoaDon.getNhanVien() != null){
            for (HoaDonChiTiet hoaDonChiTiet: hoaDonChiTiets) {
                var soLuongBan = hoaDonChiTiet.getSoLuong();
                var sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
                var soLuongSauHuy = sanPhamChiTiet.getSoLuong() + soLuongBan;
                sanPhamChiTiet.setSoLuong(soLuongSauHuy);

                sanPhamChiTietRepository.save(sanPhamChiTiet);
            }
        }

        hoaDon = repo.save(hoaDon);
        thaoTacHoaDonService.add(thaoTacHoaDon);


        return ResponseEntity.ok("ThanhCong");
    }

    public Object getByKhachHang(HoaDonByKhachHang request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        var hoaDonPage = repo.findAllByKhachHangIdOrderByNgayTaoDesc(request.getIdKhachHang(),pageable);

        NormalTableResponse<HoaDon> thongBaoNormalTableResponse = new NormalTableResponse<>();
        thongBaoNormalTableResponse.setItem(hoaDonPage.getContent());
        thongBaoNormalTableResponse.setPage(request.getPage());
        thongBaoNormalTableResponse.setPageSize(request.getPageSize());
        thongBaoNormalTableResponse.setTotalItem(hoaDonPage.getTotalElements());
        return thongBaoNormalTableResponse;
    }

    public Object getHoaDonHoanThanhByKhachHang(HoaDonByKhachHang request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        var hoaDonPage = repo.findAllByTrangThaiAndKhachHangIdOrderByNgayTaoDesc(6, request.getIdKhachHang(),pageable);

        NormalTableResponse<HoaDon> thongBaoNormalTableResponse = new NormalTableResponse<>();
        thongBaoNormalTableResponse.setItem(hoaDonPage.getContent());
        thongBaoNormalTableResponse.setPage(request.getPage());
        thongBaoNormalTableResponse.setPageSize(request.getPageSize());
        thongBaoNormalTableResponse.setTotalItem(hoaDonPage.getTotalElements());
        return thongBaoNormalTableResponse;
    }

    public Object getHoaDonChiTietChoViewKhachHang(Long idHoaDon) {
        var hoaDon = repo.findById(idHoaDon).orElse(null);
        if(hoaDon == null)return null;

        var hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoaDon_Id(hoaDon.getId());

        var thaoTacHoaDonList = thaoTacHoaDonRepository.findAllByHoaDonId(hoaDon.getId());

        HoaDonChiTietChoViewKhachHang hoaDonChiTietChoViewKhachHang = new HoaDonChiTietChoViewKhachHang();
        hoaDonChiTietChoViewKhachHang.setHoaDon(hoaDon);
        hoaDonChiTietChoViewKhachHang.setHoaDonChiTietList(hoaDonChiTietList);
        hoaDonChiTietChoViewKhachHang.setThaoTacHoaDons(thaoTacHoaDonList);
        return hoaDonChiTietChoViewKhachHang;
    }

    public DoanhSo getDoanhSoThangNay(LocalDateTime dauThang, LocalDateTime cuoiThang) {
//        var dauThang = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0).withMonth(11);
//        var cuoiThang = dauThang.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        var hoaDonList = repo.findAllByNgayThanhToanAndTrangThai(dauThang,cuoiThang);

        var soHoaDonHoanThanhTrongThang = hoaDonList.stream()
                .filter(hoaDon -> hoaDon.getMa().startsWith("HD") && !hoaDon.getMa().startsWith("HDDT"))
                .collect(Collectors.toList());
        int soLuongHoaDonHoanThanhTrongThang = soHoaDonHoanThanhTrongThang.size();
        var doanhSo = hoaDonList.stream()
                .filter(hoaDon -> hoaDon.getMa().startsWith("HD") && !hoaDon.getMa().startsWith("HDDT"))
                .mapToInt(value -> value.getTongTien().intValue())
                .sum();
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        soHoaDonHoanThanhTrongThang.forEach(hoaDon -> {
            var hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon(hoaDon);
            hoaDonChiTietList.addAll(hoaDonChiTiet);
        });

        var sanPhamDaBan = hoaDonChiTietList.stream().mapToInt(value -> value.getSoLuong()).sum();
        DoanhSo response = new DoanhSo();
        response.setHoaDon(soLuongHoaDonHoanThanhTrongThang);
        response.setDoanhSo(doanhSo);
        response.setSoSanPhamBan(sanPhamDaBan);

        return response;
    }

    public Object sanPhamBanChay() {
        var dauThang = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        var cuoiThang = dauThang.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        var hoaDonList = repo.findAllByNgayThanhToanAndTrangThai(dauThang,cuoiThang);

        var soHoaDonHoanThanhTrongThang = hoaDonList.stream()
                .filter(hoaDon -> hoaDon.getMa().startsWith("HD") && !hoaDon.getMa().startsWith("HDDT"))
                .collect(Collectors.toList());
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        soHoaDonHoanThanhTrongThang.forEach(hoaDon -> {
            var hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon(hoaDon);
            hoaDonChiTietList.addAll(hoaDonChiTiet);
        });

        // Nhóm theo sản phẩm và tính tổng số lượng
        Map<Long, Integer> productQuantityMap = hoaDonChiTietList.stream()
                .collect(Collectors.groupingBy(
                        hoaDonChiTiet -> hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getId(), // Lấy ID sản phẩm từ SanPham
                        Collectors.summingInt(HoaDonChiTiet::getSoLuong) // Tính tổng số lượng
                ));

        // Chuyển đổi Map sang List<Top5SanPham>
        List<Top5SanPham> top5SanPhams = productQuantityMap.entrySet().stream()
                .map(entry -> {
                    Long productId = entry.getKey();
                    int totalQuantity = entry.getValue();

                    // Tìm tên sản phẩm từ ID
                    String productName = sanPhamRepository.findById(productId).get().getTen(); // Tìm tên sản phẩm

                    Top5SanPham top5 = new Top5SanPham();
                    top5.setTenSanPham(productName);
                    top5.setSoLuong(totalQuantity);
                    return top5;
                })
                .sorted((p1, p2) -> Integer.compare(p2.getSoLuong(), p1.getSoLuong())) // Sắp xếp giảm dần
                .limit(5) // Lấy 5 sản phẩm bán chạy nhất
                .collect(Collectors.toList());


        return top5SanPhams;
    }

    public Object getDoanhSo() {
        List<DoanhSo> response = new ArrayList<>();
        var dauThang = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        var cuoiThang = dauThang.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        response.add(getDoanhSoThangNay(dauThang, cuoiThang));
        var dauNgay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        var cuoiNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        response.add(getDoanhSoThangNay(dauNgay, cuoiNgay));

        return response;
    }

    public Object getDoanhSoThang() {
        var now = LocalDateTime.now();
        List<DoanhSoThang> res = new ArrayList<>();
        for (int i = 1; i <= now.getDayOfMonth(); i++) {
            DoanhSoThang doanhSoThang = new DoanhSoThang();
            var dauNgay = LocalDateTime.now().withDayOfMonth(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
            var cuoiNgay = LocalDateTime.now().withDayOfMonth(i).withHour(23).withMinute(59).withSecond(59);
            var getDoanhSo = getDoanhSoThangNay(dauNgay, cuoiNgay);
            doanhSoThang.setNgayThang(i);
            doanhSoThang.setDoanhSo(getDoanhSo.getDoanhSo());
            res.add(doanhSoThang);
        }
        return res;
    }

    public List<HoaDon> getHoaDonHomNay() {
        var dauNgay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        var cuoiNgay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        var hoaDonList = repo.findAllByNgayThanhToanAndTrangThai(dauNgay,cuoiNgay);

        return hoaDonList;
    }
}
