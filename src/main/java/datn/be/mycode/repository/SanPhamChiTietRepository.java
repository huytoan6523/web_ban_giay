package datn.be.mycode.repository;

import datn.be.mycode.entity.SanPhamChiTiet;
import datn.be.mycode.request.customRequest.TableSanPhamChiTietRequest;
import datn.be.mycode.request.customRequest.TableSanPhamRequest;
import datn.be.mycode.response.SanPhamChiTietResponse;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet, Long> {

    @Query("SELECT new datn.be.mycode.response.SanPhamChiTietResponse(spct.id, spct.ngayTao, spct.ngaySua, spct.hinhAnh, spct.giaNhap, spct.giaBan, spct.soLuong, spct.trangThai, spct.sanPham, spct.mauSac, spct.kichCo) " +
            "FROM SanPhamChiTiet spct " +
            "WHERE " +
//            "(:ten IS null OR sp.ten LIKE %:ten%) AND " +
            "(:#{#request.startDate} IS NULL OR spct.ngayTao >= :#{#request.startDate}) AND " +
            "(:#{#request.endDate} IS NULL OR spct.ngayTao <= :#{#request.endDate}) AND " +
            "(:#{#request.status} IS NULL OR spct.trangThai = :#{#request.status}) AND " +
            "(:#{#request.sanPham} IS NULL OR spct.sanPham.id = :#{#request.sanPham}) AND " +
            "(:#{#request.mauSac} IS NULL OR spct.mauSac.id = :#{#request.mauSac}) AND " +
            "(:#{#request.kichCo} IS NULL OR spct.kichCo.id = :#{#request.kichCo})  "

    )
    Page<SanPhamChiTietResponse> searchAll(@Param("request") TableSanPhamChiTietRequest request, Pageable pageable);


    @Query("SELECT new datn.be.mycode.response.SanPhamChiTietResponse(spct.id, spct.ngayTao, spct.ngaySua, spct.hinhAnh, spct.giaNhap, spct.giaBan, spct.soLuong, spct.trangThai, spct.sanPham, spct.mauSac, spct.kichCo) " +
            "FROM SanPhamChiTiet spct " +
            "WHERE " +
//            "(:ten IS null OR sp.ten LIKE %:ten%) AND " +
            "(:#{#request.startDate} IS NULL OR spct.ngayTao >= :#{#request.startDate}) AND " +
            "(:#{#request.endDate} IS NULL OR spct.ngayTao <= :#{#request.endDate}) AND " +
            "(:#{#request.status} IS NULL OR spct.trangThai = :#{#request.status}) AND " +
            "(:#{#request.sanPham} IS NULL OR spct.sanPham.id = :#{#request.sanPham}) AND " +
            "(:#{#request.mauSac} IS NULL OR spct.mauSac.id = :#{#request.mauSac}) AND " +
            "(:#{#request.kichCo} IS NULL OR spct.kichCo.id = :#{#request.kichCo})  AND " +
            "spct.soLuong <= 10 " +
            "ORDER BY spct.soLuong ASC"
    )
    Page<SanPhamChiTietResponse> searchAllSortedBySoLuong(@Param("request") TableSanPhamChiTietRequest request, Pageable pageable);


    List<SanPhamChiTiet> findAllBySanPham_IdAndTrangThai(Long idSanPham, int trangThai);

    List<SanPhamChiTiet> findAllBySanPham_IdAndMauSacId(Long idSanPham, Long idMau);

    List<SanPhamChiTiet> findAllBySanPham_Id(Long idSanPham);

    @Query("SELECT min(spct.giaBan) " +
            "FROM SanPhamChiTiet spct " +
            "WHERE " +
            "spct.sanPham.id = :idSanPham AND " +
            "spct.trangThai = 1"
    )
    BigDecimal findGiaMinByIdSanPham(Long idSanPham);


    @Query("SELECT max(spct.giaBan) " +
            "FROM SanPhamChiTiet spct " +
            "WHERE " +
            "spct.sanPham.id = :idSanPham AND " +
            "spct.trangThai = 1"
    )
    BigDecimal findGiaMaxByIdSanPham(Long idSanPham);

    @Query("SELECT COALESCE(SUM(spct.soLuong), 0) " +
            "FROM SanPhamChiTiet spct " +
            "WHERE " +
            "spct.sanPham.id = :idSanPham AND " +
            "spct.trangThai = 1"
    )
    int sumSoLuongByTrangThai(Long idSanPham);

    List<SanPhamChiTiet> findAllBySoLuong(int soLuong);

    @Query("SELECT COUNT(s) > 0 FROM SanPhamChiTiet s " +
            "WHERE s.sanPham.id = :sanPhamId " +
            "AND s.kichCo.id = :kichCoId " +
            "AND s.mauSac.id = :mauSacId " +
            "AND (:sanPhamChiTietId IS NULL OR s.id != :sanPhamChiTietId)")
    boolean existsBySanPhamAndKichCoAndMauSac(
            @Param("sanPhamId") Long sanPhamId,
            @Param("kichCoId") Long kichCoId,
            @Param("mauSacId") Long mauSacId,
            @Param("sanPhamChiTietId") Long sanPhamChiTietId);

    @Query("SELECT DISTINCT sct.mauSac.id FROM SanPhamChiTiet sct WHERE sct.sanPham.id = :idSanPham")
    List<Long> findDistinctMauSacBySanPhamId(@Param("idSanPham") Long idSanPham);

    @Query("SELECT DISTINCT sct.kichCo.id FROM SanPhamChiTiet sct WHERE sct.sanPham.id = :idSanPham")
    List<Long> findDistinctKichCoBySanPhamId(@Param("idSanPham") Long idSanPham);
}
