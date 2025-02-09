package datn.be.mycode.repository;

import datn.be.mycode.entity.LoaiCo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiCoRepository extends JpaRepository<LoaiCo,Long> {

    Page<LoaiCo> findAllByTrangThai(int trangThai, Pageable pageable);

    @Query("SELECT u FROM LoaiCo u WHERE u.ten LIKE %:name%")
    Page<LoaiCo> searchByTen(String name, Pageable pageable);


    List<LoaiCo> findAllByTrangThai(int i);

    Optional<LoaiCo> findByTen(String ten);
}
