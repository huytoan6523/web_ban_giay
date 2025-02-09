package datn.be.mycode.repository;

import datn.be.mycode.entity.SanPhamDoiTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamDoiTraRepository extends JpaRepository<SanPhamDoiTra, Long> {

    List<SanPhamDoiTra> findAllByPhieuDoiTraId(Long idPhieuDoiTra);
}
