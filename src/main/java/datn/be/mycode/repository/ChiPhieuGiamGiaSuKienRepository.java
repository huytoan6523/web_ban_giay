package datn.be.mycode.repository;


import datn.be.mycode.entity.ChiPhieuGiamGiaSuKien;
import datn.be.mycode.entity.SuKien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiPhieuGiamGiaSuKienRepository extends JpaRepository<ChiPhieuGiamGiaSuKien,Long> {
    @Query("SELECT c FROM ChiPhieuGiamGiaSuKien c WHERE c.id  =:id")
    Page<ChiPhieuGiamGiaSuKien> findAllById(@Param("id")Long id, Pageable pageable);

    @Query("SELECT c FROM ChiPhieuGiamGiaSuKien c WHERE c.id = :id")
    Optional<ChiPhieuGiamGiaSuKien> searchById(@Param("id") Long id);

    Page<ChiPhieuGiamGiaSuKien> findAllByTrangThai(int trangThai, Pageable pageable);

    List<ChiPhieuGiamGiaSuKien> findAllBySuKienId(Long idSuKien);

    List<ChiPhieuGiamGiaSuKien> findBySuKienIdAndVoucherId(Long idChiPhieuGiamGia, Long idVoucher);
}
