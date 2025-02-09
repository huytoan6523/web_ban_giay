package datn.be.mycode.repository;

import datn.be.mycode.entity.GioHang;
import datn.be.mycode.entity.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {

    Page<GioHangChiTiet> findAllByGioHangId(Long idGioHang, Pageable pageable);

    List<GioHangChiTiet> findAllByGioHangId(Long idGioHang);

    Optional<GioHangChiTiet> findByGioHangIdAndAndSanPhamChiTietId(Long idGioHangChiTiet, Long isSanPhamChiTiet);

    @Query("SELECT COALESCE(SUM(ghct.soLuong), 0) " +
            "FROM GioHangChiTiet ghct " +
            "WHERE ghct.gioHang.id = :idHoaDon")
    int getTongSoLuong(Long idHoaDon);
}
