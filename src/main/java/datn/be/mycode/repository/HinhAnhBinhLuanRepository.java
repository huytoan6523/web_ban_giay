package datn.be.mycode.repository;

import datn.be.mycode.entity.HinhAnhBinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HinhAnhBinhLuanRepository extends JpaRepository<HinhAnhBinhLuan,Long> {

    void deleteByDanhGiaSanPham_Id(Long idDanhGiaSanPham);
}
