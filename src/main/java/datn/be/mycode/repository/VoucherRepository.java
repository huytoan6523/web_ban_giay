package datn.be.mycode.repository;



import datn.be.mycode.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    Page<Voucher> findAllByTrangThai(int trangThai, Pageable pageable);
    Page<Voucher> findAll(Pageable pageable);

    Page<Voucher> findAllByTheLoai(int theLoai, Pageable pageable);

    List<Voucher> findAllByTheLoai(int theLoai);

    Optional<Voucher> findByMa(String ma);

    @Query("SELECT u FROM Voucher u WHERE u.ten LIKE %:query% OR u.ma LIKE %:query%")
    Page<Voucher> searchByTenOrMa(@Param("query") String query, Pageable pageable);


    @Query("SELECT u FROM Voucher u WHERE u.id = :id")
    Optional<Voucher> searchById(@Param("id") Long id);

    // Tìm kiếm theo khoảng ngày tạo
    @Query("SELECT v FROM Voucher v WHERE v.ngayTao BETWEEN :startDate AND :endDate")
    Page<Voucher> searchByNgayTao(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Tìm kiếm theo phần trăm giảm
    @Query("SELECT v FROM Voucher v WHERE v.phanTramGiam = :phanTramGiam")
    Page<Voucher> searchByPhanTramGiam(int phanTramGiam, Pageable pageable);

    Page<Voucher> findByNgayBatDauLessThanEqualAndNgayKetThucGreaterThanEqual(LocalDateTime today1, LocalDateTime today2,Pageable pageable);

    // Truy vấn lấy các voucher có thể loại và giá giảm phù hợp
    @Query("SELECT v FROM Voucher v WHERE " +
            "v.theLoai = :theLoai AND " +
            "v.giaTriGiamToiThieu <= :giaGiam AND " +
            "v.soLuong > 0 AND " +
            "v.trangThai = 1 " +
            "ORDER BY v.giamToiDa desc")
    Page<Voucher> findVoucherActive(@Param("theLoai") int theLoai, @Param("giaGiam") BigDecimal giaGiam, Pageable pageable);

    @Query("SELECT v FROM Voucher v " +
            "WHERE v.theLoai = :theLoai " +
            "AND v.ngayBatDau <= :currentDate " +
            "AND v.ngayKetThuc >= :currentDate " +
            "AND v.trangThai = 1")
    Page<Voucher> findTheLoaiActiveVouchers(@Param("theLoai") int theLoai, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Query("SELECT v FROM Voucher v WHERE v.theLoai = 0")
    Page<Voucher> getVoucherHoaDon(Pageable pageable);
    @Query("SELECT v FROM Voucher v WHERE v.theLoai = 1")
    Page<Voucher> getVoucherShip(Pageable pageable);

}
