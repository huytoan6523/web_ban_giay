package datn.be.mycode.repository;

import datn.be.mycode.entity.ThongBao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {

    List<ThongBao> findAllByKhachHangId(Long idkhachHang);

//    @Query("SELECT t FROM ThongBao t WHERE t.khachHang.id = :khachHangId ORDER BY t.ngayTao desc , t.daDoc ASC")
    @Query("SELECT t FROM ThongBao t WHERE t.khachHang.id = :khachHangId ORDER BY CASE WHEN t.daDoc = true THEN 1 ELSE 0 END, t.ngayTao DESC")
    Page<ThongBao> findAllByKhachHangIdOrderByNgayTaoAndDaDoc(Long khachHangId, Pageable pageable);

    @Query("SELECT COUNT(t) FROM ThongBao t WHERE t.daDoc = false AND t.khachHang.id = :khachHangId")
    Integer countByDaDocFalse(Long khachHangId);
}
