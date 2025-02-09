package datn.be.mycode.repository;

import datn.be.mycode.entity.DanhGiaSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DanhGiaSanPhamRepository extends JpaRepository<DanhGiaSanPham,Long> {

    Page<DanhGiaSanPham> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT d FROM DanhGiaSanPham d WHERE d.idSanPham.id = :idSanPham")
    List<DanhGiaSanPham> findByIdSanPham(@Param("idSanPham") Long idSanPham);


    @Query("SELECT l FROM DanhGiaSanPham l WHERE l.id  =:id")
    Page<DanhGiaSanPham> findAllById(@Param("id")Long id, Pageable pageable);

    @Query("SELECT d FROM DanhGiaSanPham d WHERE d.idKhachHang.id = :idKhachHang")
    Page<DanhGiaSanPham> findDanhGiaByKhachHangId(@Param("idKhachHang") Long idKhachHang, Pageable pageable);

    @Query("SELECT d FROM DanhGiaSanPham d WHERE d.idSanPham.id = :idSanPham ORDER BY d.ngayTao DESC")
    Page<DanhGiaSanPham> findDanhGiaBySanPhamId(@Param("idSanPham") Long idSanPham, Pageable pageable);

    void deleteById(Long id);


    @Query("SELECT d.sao, COUNT(d.id) FROM DanhGiaSanPham d WHERE d.idSanPham.id = :idSanPham GROUP BY d.sao")
    Map<Integer, Long> thongKeDanhGiaTheoSao(@Param("idSanPham") Long idSanPham);

    @Query("SELECT COUNT(d.id) FROM DanhGiaSanPham d WHERE d.idSanPham.id = :idSanPham AND d.binhLuan IS NOT NULL")
    Long demDanhGiaCoBinhLuan(@Param("idSanPham") Long idSanPham);

    @Query("SELECT COUNT(d.id) FROM DanhGiaSanPham d WHERE d.idSanPham.id = :idSanPham AND d.hinhAnhBinhLuan IS NOT EMPTY")
    Long demDanhGiaCoHinhAnh(@Param("idSanPham") Long idSanPham);

    List<DanhGiaSanPham> findByIdSanPham_Id(Long idSanPham);

}
