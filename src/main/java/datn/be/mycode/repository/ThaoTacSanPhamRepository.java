package datn.be.mycode.repository;

import datn.be.mycode.entity.ThaoTacSanPham;
import datn.be.mycode.entity.TheLoai;
import datn.be.mycode.response.SanPhamResponse;
import datn.be.mycode.response.ThaoTacSanPhamResPonse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ThaoTacSanPhamRepository extends JpaRepository<ThaoTacSanPham,Long> {

    @Query("SELECT new datn.be.mycode.response.ThaoTacSanPhamResPonse(ttspLEFT.id,ttspLEFT.thaoTac, ttspLEFT.ngayTao, ttspLEFT.trangThai)" +
            "FROM ThaoTacSanPham ttsp" +
            "LEFT JOIN NhanVien nv ON ttspLEFT.id = nv.id " +
            "WHERE " +
            "(:thaoTac IS null OR ttspLEFT.thaoTac LIKE %:thaoTac%) AND " +
            "(:startDate IS NULL OR ttspLEFT.ngayTao >= :startDate) AND " +

            "(:status IS NULL OR ttspLEFT.trangThai = :status)" +
            "GROUP BY ttspLEFT.id,ttspLEFT.ngayTao, ttspLEFT.thaoTac, ttspLEFT.trangThai"
    )

    Page<ThaoTacSanPhamResPonse> searchAll(String thaoTac, LocalDateTime startDate,  Integer status, Pageable pageable);

}
