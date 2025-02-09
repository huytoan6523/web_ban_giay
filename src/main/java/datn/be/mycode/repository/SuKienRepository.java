package datn.be.mycode.repository;

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
public interface SuKienRepository extends JpaRepository<SuKien,Long> {
    @Query("SELECT s FROM SuKien s WHERE s.id  =:id")
    Page<SuKien> findAllById(@Param("id")Long id, Pageable pageable);

    @Query("SELECT s FROM SuKien s WHERE s.id = :id")
    Optional<SuKien> searchById(@Param("id") Long id);

    Page<SuKien> findAllByTrangThai(int trangThai, Pageable pageable);

    Optional<SuKien> findByTrangThai(int trangThai);

    @Query("SELECT t FROM SuKien t WHERE t.thangHoatDong IS NOT NULL AND t.thangHoatDong <> ''")
    List<SuKien> findAllWithActiveMonths();
}
