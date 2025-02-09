package datn.be.mycode.repository;

import datn.be.mycode.entity.LichSuSuDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LichSuSuDungRepository extends JpaRepository<LichSuSuDung,Long> {
    @Query("SELECT l FROM LichSuSuDung l WHERE l.id  =:id")
    Page<LichSuSuDung> findAllById(@Param("id")Long id, Pageable pageable);

    Page<LichSuSuDung> findAllByTrangThai(int trangThai, Pageable pageable);

    List<LichSuSuDung> findAllByKhachHangId(Long idKhachHang);

    List<LichSuSuDung> findAllByChiPhieuGiamGiaSuKienIdAndKhachHangId(Long idChiPhieuGiamGia, Long idKhachHang);
}
