package datn.be.mycode.service;


import datn.be.mycode.entity.Voucher;
import datn.be.mycode.repository.VoucherRepository;
import datn.be.mycode.request.VoucherRequest;
import datn.be.mycode.request.customRequest.TableVoucherActiveRequest;
import datn.be.mycode.request.customRequest.TableVoucherHoaDonRequest;
import datn.be.mycode.request.customRequest.TableVoucherRequest;
import datn.be.mycode.request.customRequest.TableVoucherStatusInRequest;
import datn.be.mycode.request.voucher.VoucherSuKien;
import datn.be.mycode.response.NormalTableResponse;
import datn.be.mycode.response.erroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    VoucherRepository repository;
    @Autowired
    private GiamGiaService giamGiaService;


    public NormalTableResponse<Voucher> getAll(TableVoucherRequest request) {

        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }


        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<Voucher> voucherPage;
        if (request.getKeyWord() != null ) {
            voucherPage = repository.searchByTenOrMa(request.getKeyWord(), pageable);
        }else if (request.getStartDate() != null & request.getStartDate() != null){
            voucherPage = repository.searchByNgayTao(request.getStartDate(),request.getEndDate(), pageable);
        }else if (request.getPhanTramGiam() != null ){
            voucherPage = repository.searchByPhanTramGiam(request.getPhanTramGiam(), pageable);
        } else if (request.getStatus() != null){
            voucherPage = repository.findAllByTrangThai(request.getStatus(), pageable);
        }else if (request.getTheLoai() != null){
            voucherPage = repository.findAllByTheLoai(request.getTheLoai(), pageable);
        }else{
            voucherPage = repository.findAll(pageable);
        }

        List<Voucher> vouchers = voucherPage.getContent().stream().toList();

        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(request.getPage());
        voucherNormalTableResponse.setPageSize(request.getPageSize());
        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());

        return voucherNormalTableResponse;
    }



    public ResponseEntity<?> add(VoucherRequest request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);

        Voucher voucher = new Voucher();
        voucher.setTen(request.getTen());
        voucher.setTheLoai(request.getTheLoai());
        voucher.setNgayBatDau(request.getNgayBatDau());
        voucher.setNgayKetThuc(request.getNgayKetThuc());
        voucher.setNgayTao(LocalDateTime.now());
        voucher.setSoLuong(request.getSoLuong());
        voucher.setPhanTramGiam(request.getPhanTramGiam());
        voucher.setGiamToiDa(request.getGiamToiDa());
        voucher.setGiaTriGiamToiThieu(request.getGiaTriGiamToiThieu());
        voucher.setTrangThai(1);

        voucher = repository.save(voucher);
        voucher.setMa("PGG"+voucher.getId());
        voucher = repository.save(voucher);
        capNhatTrangThaiVoucher();
        return ResponseEntity.ok(voucher);
    }

    private String check(VoucherRequest request) {
        var now = LocalDateTime.now();
        if (request.getTen().isBlank()) return "Bạn chưa nhập tên";
        if (request.getPhanTramGiam()>100) return "Phần trăm giảm phải dưới 100";
        if (request.getSoLuong()<=0) return "Số lượng phải lớn hơn 0";
        if (request.getPhanTramGiam()<=0) return "Số lượng phải lớn hơn 0";
        if (request.getNgayKetThuc().isBefore(request.getNgayBatDau())) return "Ngày kết thúc phải sau hôm nay";
        return "";
    }

    public ResponseEntity<?> update(VoucherRequest request) {
        String erroMess = check(request);
        if (!erroMess.isBlank()) return getErro(erroMess);

        Voucher voucher = repository.findById(request.getId()).get();
        voucher.setTen(request.getTen());
        voucher.setTheLoai(request.getTheLoai());
        voucher.setNgayBatDau(request.getNgayBatDau());
        voucher.setNgayKetThuc(request.getNgayKetThuc());
        voucher.setNgaySua(LocalDateTime.now());
        voucher.setSoLuong(request.getSoLuong());
        voucher.setPhanTramGiam(request.getPhanTramGiam());
        voucher.setGiamToiDa(request.getGiamToiDa());
        voucher.setGiaTriGiamToiThieu(request.getGiaTriGiamToiThieu());
        voucher.setTrangThai(request.getTrangThai());

        voucher = repository.save(voucher);
        capNhatTrangThaiVoucher();
        return ResponseEntity.ok(voucher);
    }

    public Voucher udateTrangThai(Long id, Integer status){
        Voucher voucher = repository.findById(id).get();
        voucher.setTrangThai(status);
        return repository.save(voucher);
    }

    public Voucher getVoucherById(Long idVoucher) {
        return repository.searchById(idVoucher).get();
    }

    /**
     * Cập nhật trạng thái của các voucher dựa trên ngày hiện tại.
     * Trạng thái = 2 nếu voucher đã quá hạn (endDay < ngày hiện tại).
     * Trạng thái = 0 nếu chưa đến sự kiện (startDay > ngày hiện tại).
     * Trạng thái = 1 nếu đang trong thời gian sự kiện (startDay <= ngày hiện tại <= endDay).
     */
    @Transactional
    public void capNhatTrangThaiVoucher() {
        LocalDateTime now = LocalDateTime.now();
        // Lấy tất cả voucher
        List<Voucher> vouchers = repository.findAll();
        for (Voucher voucher : vouchers) {
            LocalDateTime startDay = voucher.getNgayBatDau();
            LocalDateTime endDay = voucher.getNgayKetThuc();

            if (endDay.isBefore(now)) {
                // Voucher đã quá hạn
                voucher.setTrangThai(0);
            } else if (startDay.isAfter(now)) {
                // Voucher chưa đến sự kiện
                voucher.setTrangThai(2);
            } else if (startDay.isBefore(now) || startDay.isEqual(now)) {
                // Voucher đang trong thời gian sự kiện (startDay <= now <= endDay)
                if (voucher.getTrangThai() != 3){
                voucher.setTrangThai(1);
                }
            }
            // Lưu lại voucher nếu trạng thái thay đổi
            repository.save(voucher);
        }
        giamGiaService.loadGiamGia();
    }

//    public NormalTableResponse<Voucher> trongSuKien(TableVoucherStatusInRequest request) {
//
//        if (request.getPage() ==  null) {
//            request.setPage(1);
//        }
//        if (request.getPageSize() == null) {
//            request.setPageSize(10);
//        }
//
//        LocalDateTime today = LocalDateTime.now();
//        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
//        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
//        Page<Voucher> voucherPage;
//            voucherPage = repository.findByNgayBatDauLessThanEqualAndNgayKetThucGreaterThanEqual(today,today, pageable);
//
//        List<Voucher> vouchers = voucherPage.getContent().stream().toList();
//
//        voucherNormalTableResponse.setItem(vouchers);
//        voucherNormalTableResponse.setPage(request.getPage());
//        voucherNormalTableResponse.setPageSize(request.getPageSize());
//        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());
//
//        return voucherNormalTableResponse;
//    }

    public NormalTableResponse<Voucher> getVoucherActive(TableVoucherActiveRequest request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<Voucher> voucherPage;
        voucherPage = repository.findVoucherActive(request.getTheLoai(),request.getGiaTriGiamToiThieu(), pageable);
        List<Voucher> vouchers = voucherPage.getContent().stream().toList();
        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(request.getPage());
        voucherNormalTableResponse.setPageSize(request.getPageSize());
        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());

        return voucherNormalTableResponse;
    }
    // Lấy danh sách các voucher active và đang trong sự kiện
    public NormalTableResponse<Voucher> checkVouchers(TableVoucherStatusInRequest request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        LocalDateTime currentDate = LocalDateTime.now();
        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<Voucher> voucherPage;
        voucherPage = repository.findTheLoaiActiveVouchers(request.getTheLoai(),currentDate, pageable);
        List<Voucher> vouchers = voucherPage.getContent().stream().toList();
        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(request.getPage());
        voucherNormalTableResponse.setPageSize(request.getPageSize());
        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());

        return voucherNormalTableResponse;
    }
    public NormalTableResponse<Voucher> getVoucherHoaDon(TableVoucherHoaDonRequest request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<Voucher> voucherPage;
        voucherPage = repository.getVoucherHoaDon( pageable);
        List<Voucher> vouchers = voucherPage.getContent().stream().toList();
        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(request.getPage());
        voucherNormalTableResponse.setPageSize(request.getPageSize());
        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());

        return voucherNormalTableResponse;
    }
    public NormalTableResponse<Voucher> getVoucherShip(TableVoucherHoaDonRequest request) {
        if (request.getPage() ==  null) {
            request.setPage(1);
        }
        if (request.getPageSize() == null) {
            request.setPageSize(10);
        }
        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        Pageable pageable = PageRequest.of(request.getPage() - 1,request.getPageSize());
        Page<Voucher> voucherPage;
        voucherPage = repository.getVoucherShip( pageable);
        List<Voucher> vouchers = voucherPage.getContent().stream().toList();
        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(request.getPage());
        voucherNormalTableResponse.setPageSize(request.getPageSize());
        voucherNormalTableResponse.setTotalItem(voucherPage.getTotalElements());

        return voucherNormalTableResponse;
    }

    public Object addVoucherSuKien(VoucherSuKien request) {
        var ketQuaValidate = validate(request);
        if( !ketQuaValidate.equals("ok")) return getErro(ketQuaValidate);

        Voucher voucher = new Voucher();

        voucher.setTen(request.getTen());
        voucher.setTheLoai(request.getTheLoai());
        voucher.setNgayTao(LocalDateTime.now());
        voucher.setNgayBatDau(LocalDateTime.parse("1753-11-10T10:15:30"));
        voucher.setNgayKetThuc(LocalDateTime.parse("5024-11-10T10:15:30"));
        voucher.setPhanTramGiam(request.getPhanTramGiam());
        voucher.setGiamToiDa(request.getGiamToiDa());
        voucher.setGiaTriGiamToiThieu(request.getGiaTriGiamToiThieu());
        voucher.setTrangThai(3);

        voucher = repository.save(voucher);
        voucher.setMa("PGG"+voucher.getId());

        return repository.save(voucher);
    }

    private String validate(VoucherSuKien request) {
        if(request.getTheLoai()== null) return "Cần chọn thể loại";
        if(request.getTen().isBlank()) return "Tên không được bỏ trống";
        if(request.getPhanTramGiam() == null) return "Phần trăm không được bỏ trống";
        if(request.getGiamToiDa() == null) return "Giảm tối đa chưa được nhập";
        if(request.getGiaTriGiamToiThieu()== null) return "Giá trị giảm tối thiểu chưa được nhập";
        return "ok";
    }

    public Object getPhieuGiamGiaVoucher(Integer page, Integer pageSize) {
        if (page ==  null) {
            page = 1 ;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(page - 1,pageSize);

        var voucherEven = repository.findAllByTrangThai(3, pageable);

        List<Voucher> vouchers = voucherEven.getContent();
        NormalTableResponse<Voucher> voucherNormalTableResponse = new NormalTableResponse<>();
        voucherNormalTableResponse.setItem(vouchers);
        voucherNormalTableResponse.setPage(page);
        voucherNormalTableResponse.setPageSize(pageSize);
        voucherNormalTableResponse.setTotalItem(voucherEven.getTotalElements());
        return voucherNormalTableResponse;
    }

    private ResponseEntity<?> getErro(String s){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new erroResponse(s));
    }

}
