package datn.be.mycode.repository;

import datn.be.mycode.entity.VoucherKhachHang;
import datn.be.mycode.response.VoucherKhachHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherKhachHangRepository extends JpaRepository<VoucherKhachHang,Long> {

    @Query("SELECT new datn.be.mycode.response.VoucherKhachHangResponse(v.voucher.ten, v.voucher.ma, " +
            "v.voucher.ngayBatDau, v.voucher.ngayKetThuc, v.voucher.phanTramGiam, v.voucher.soLuong, " +
            "(v.voucher.soLuong > 0 AND CURRENT_DATE BETWEEN v.voucher.ngayBatDau AND v.voucher.ngayKetThuc), " +
            "v.ngayTao) " +
            "FROM VoucherKhachHang v " +
            "WHERE v.khachHang.ma = :maKhachHang " +
            "ORDER BY v.ngayTao DESC")
    Page<VoucherKhachHangResponse> searchVoucherByKhachHangMa(String maKhachHang, Pageable pageable);

    @Query("SELECT new datn.be.mycode.response.VoucherKhachHangResponse(v.voucher.ten, v.voucher.ma, " +
            "v.voucher.ngayBatDau, v.voucher.ngayKetThuc, v.voucher.phanTramGiam, v.voucher.soLuong, " +
            "(v.voucher.soLuong > 0 AND CURRENT_DATE BETWEEN v.voucher.ngayBatDau AND v.voucher.ngayKetThuc), " +
            "v.ngayTao) " +
            "FROM VoucherKhachHang v " +
            "WHERE  v.trangThai = :trangThai " +
            "ORDER BY v.ngayTao DESC")
    Page<VoucherKhachHangResponse> findAllByTrangThai(Integer trangThai, Pageable pageable);

    List<VoucherKhachHang> findAllByKhachHangId(Long idKhachHang);

    Optional<VoucherKhachHang> findByVoucherId(Long idVoucher);
}
