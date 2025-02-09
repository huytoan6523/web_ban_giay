package datn.be.mycode.repository;

import datn.be.mycode.entity.HoaDon;
import datn.be.mycode.entity.HoaDonChiTiet;
import datn.be.mycode.entity.SanPham;
import org.eclipse.tags.shaded.org.apache.bcel.generic.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {

    HoaDonChiTiet findByHoaDonIdAndSanPhamChiTietId(Long idHoaDon, Long idSanPhamChiTiet);

    List<HoaDonChiTiet> findAllBySanPhamChiTiet_IdAndTrangThai(Long idSanPhamChiTiet, int trangThai);

    List<HoaDonChiTiet> findAllByHoaDon_Id(Long idHoaDon);

    List<HoaDonChiTiet> findBySanPhamChiTiet_Id(Long idSanPhamChiTiet);
    List<HoaDonChiTiet> findBySanPhamChiTiet_SanPham_Id(Long idSanPham);
    List<HoaDonChiTiet> findByHoaDon(HoaDon hoaDon);


    Page<HoaDonChiTiet> findAllByHoaDon_Id(Long idHoaDon, Pageable pageable);

    @Query("SELECT DISTINCT hdc.sanPhamChiTiet.sanPham FROM HoaDonChiTiet hdc " +
            "JOIN hdc.hoaDon hd " +
            "WHERE hdc.danhGia = false " +
            "AND hd.ngayThanhToan >= :startDate " +
            "AND hd.ngayThanhToan <= :endDate")
    List<SanPham> timSanPhamChuaCmtTrong7NgayThanhToan(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

//    @Query("select sum(b.soLuong)" +
//            "from HoaDon a JOIN HoaDonChiTiet b ON a.id = b.hoaDon.id" +
//            " join SanPhamChiTiet c on b.sanPhamChiTiet.id = c.id" +
//            " Where b.sanPhamChiTiet.id = :idSanPham" +
//            " And a.trangThai>3  a.trangThai< 6"
//    )
//    Integer soLuongSanPham(  @Param("idSanPham") Long id);

    @Query("SELECT DISTINCT hdc.sanPhamChiTiet.sanPham " +
            "FROM HoaDonChiTiet hdc " +
            "WHERE hdc.hoaDon.khachHang.id = :idKhachHang " +
            "AND hdc.danhGia = false " +
            "AND hdc.hoaDon.ngayThanhToan IS NOT NULL " +
            "AND hdc.hoaDon.ngayThanhToan BETWEEN :startDate AND :endDate")
    List<SanPham> getNhungSanPhamTrongHoaDonChiTietIsCMTFalseByIdKh(
            @Param("idKhachHang") Long idKhachHang,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Lọc các sản phẩm chưa được đánh giá (isCMT = false) theo idKhachHang và trong vòng 7 ngày kể từ ngàyThanhToan
    @Query("SELECT hdc FROM HoaDonChiTiet hdc " +
            "WHERE hdc.hoaDon.khachHang.id = :idKhachHang " +
            "AND hdc.danhGia = false " +
            "AND hdc.hoaDon.ngayThanhToan IS NOT NULL " +
            "AND hdc.hoaDon.ngayThanhToan >= :sevenDaysAgo")
    List<HoaDonChiTiet> findAllByKhachHangIdAndIsCMTFalseAndIn7Days(
            @Param("idKhachHang") Long idKhachHang,
            @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);

    @Query("SELECT hdc " +
            "FROM HoaDonChiTiet hdc " +
            "JOIN FETCH hdc.hoaDon hd " +
            "JOIN FETCH hd.khachHang kh " +
            "WHERE kh.id = :idKhachHang AND hdc.danhGia = false " +
            "AND hd.ngayTao >= :sevenDaysAgo")
    List<HoaDonChiTiet> findAllByKhachHangIdAndWithin7Days(@Param("idKhachHang") Long idKhachHang,
                                                           @Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);



}
