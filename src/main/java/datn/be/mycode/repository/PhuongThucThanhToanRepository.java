package datn.be.mycode.repository;

import datn.be.mycode.entity.PhuongThucThanhToan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan, Long> {

    Page<PhuongThucThanhToan> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM PhuongThucThanhToan u " +
            "WHERE " +
            "(:ten IS null OR u.ten LIKE %:ten%) AND " +
            "(:trangThai IS null OR u.trangThai = :trangThai)"
            )
    Page<PhuongThucThanhToan> searchByTen(String ten, int trangThai, Pageable pageable);
}
