package datn.be.mycode.repository;

import datn.be.mycode.entity.HopThoaiKhachHang;
import datn.be.mycode.entity.HopThoaiNhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HopThoaiKhachHangRepository extends JpaRepository<HopThoaiKhachHang, Long> {

    @Query("SELECT h FROM HopThoaiKhachHang h WHERE " +
            "(h.nhanVien.id = :nhanVienId AND (:keyWord IS null OR h.khachHang.soDienThoai LIKE :keyWord OR h.khachHang.hoTen LIKE %:keyWord%)) " +
            "ORDER BY h.thoiGianNhanCuoi DESC"
    )
    List<HopThoaiKhachHang> findByIdNhanVienAndKeyword(@Param("nhanVienId") Long nhanVienId, @Param("keyWord") String keyWord);

    List<HopThoaiKhachHang> findAllByKhachHangIdOrderByThoiGianNhanCuoiDesc(Long idKhachHang);

    Optional<HopThoaiKhachHang> findByNhanVienIdAndAndKhachHangId(Long idNhanVien, Long idKhachHang);
}
