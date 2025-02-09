package datn.be.mycode.repository;

import datn.be.mycode.entity.ThongTinLyDoDoiTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongTinLyDoDoiTraRepository extends JpaRepository<ThongTinLyDoDoiTra, Long> {

    List<ThongTinLyDoDoiTra> findAllByPhieuDoiTraId(Long idPhieuDoiTra);
}
