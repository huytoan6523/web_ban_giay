package datn.be.mycode.repository;

import datn.be.mycode.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("SELECT hd " +
            "FROM HoaDon hd " +
            "WHERE " +
            "(:ma IS null OR hd.ma LIKE %:ma%) AND"+
            "(:startDate IS NULL OR hd.ngayTao >= :startDate) AND " +
            "(:endDate IS NULL OR hd.ngayTao <= :endDate) AND " +
            "(:idKhachHang IS NULL OR hd.khachHang.id = :idKhachHang) AND " +
            "(:idNhanVien IS NULL OR hd.nhanVien.id = :idNhanVien) AND " +
            "hd.trangThai = :trangThai " +
            "ORDER BY hd.ngayTao DESC"
    )
    Page<HoaDon> searchAll(String ma, LocalDateTime startDate, LocalDateTime endDate, Long idKhachHang, Long idNhanVien, int trangThai, Pageable pageable);

    Page<HoaDon> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM HoaDon u WHERE u.id = :id")
    Optional<HoaDon> searchById(@Param("id") Long id);

//    @Query("SELECT h FROM HoaDon h WHERE h.khachHang.id = :idKhachHang AND h.ngayThanhToan >= CURRENT_DATE - INTERVAL '7' DAY")
//    List<HoaDon> findRecentHoaDonsByKhachHangId(@Param("idKhachHang") Long idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.khachHang.id = :idKhachHang AND h.ngayThanhToan >= :sevenDaysAgo")
    List<HoaDon> findRecentHoaDonsByKhachHangId(@Param("idKhachHang") Long idKhachHang, @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    Page<HoaDon> findAllByKhachHangIdOrderByNgayTaoDesc(Long idKhachHang, Pageable pageable);

    Page<HoaDon> findAllByTrangThaiAndKhachHangIdOrderByNgayTaoDesc(int trangThai, Long idKhachHang, Pageable pageable);

    List<HoaDon> findByKhachHangId(@Param("idKhachHang") Long idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.ngayThanhToan BETWEEN :dauThang AND :cuoiThang AND h.trangThai = 6")
    List<HoaDon> findAllByNgayThanhToanAndTrangThai(
            @Param("dauThang") LocalDateTime dauThang,
            @Param("cuoiThang") LocalDateTime cuoiThang);
}
